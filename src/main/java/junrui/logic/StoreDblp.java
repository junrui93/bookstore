package junrui.logic;

import junrui.mapper.PersonMapper;
import junrui.mapper.PublPersonMapper;
import junrui.mapper.PublTypeMapper;
import junrui.mapper.PublicationMapper;
import junrui.model.Person;
import junrui.model.PublPerson;
import junrui.model.PublType;
import junrui.model.Publication;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class StoreDblp {

    private static final Logger logger = LoggerFactory.getLogger(StoreDblp.class);

    @Autowired
    private PublicationMapper publMapper;

    @Autowired
    private PublTypeMapper publTypeMapper;

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private PublPersonMapper publPersonMapper;

    private Map<String, Integer> publTypeMap;

    public void initPublTypeMap() {
        publTypeMap = new HashMap<>();
        for (PublType publType : publTypeMapper.selectAll()) {
            publTypeMap.put(publType.getName(), publType.getId());
        }
    }

    public Publication readPublicationFromElement(Element element) {
        Publication publ = new Publication();
        if (publTypeMap == null) {
            initPublTypeMap();
        }
        publ.setTypeId(publTypeMap.get(element.getName()));
        publ.setRemoved(false);
        publ.setPrice(BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(5, 150)));

        List<Person> authors = new ArrayList<>();
        List<Person> editors = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();

        for (Element child : element.elements()) {
            if ("title".equals(child.getName())) {
                final int maxLength = 1000;
                String title = child.getStringValue();
                if (title.length() > maxLength) {
                    title = title.substring(0, maxLength);
                }
                publ.setTitle(title);
            } else if ("booktitle".equals(child.getName()) ||
                    "journal".equals(child.getName())) {
                publ.setVenue(child.getStringValue());
            } else if ("year".equals(child.getName())) {
                int year = 0;
                try {
                    year = Integer.parseInt(child.getStringValue());
                } catch (NumberFormatException e) {
                    logger.warn(e.getMessage());
                }
                publ.setYear(year);
            } else if ("author".equals(child.getName())) {
                Person author = new Person();
                author.setType(0);
                author.setName(child.getStringValue());
                for (Attribute attr : child.attributes()) {
                    if ("aux".equals(attr.getName())) {
                        author.setAux(attr.getStringValue());
                    } else if ("bibtex".equals(attr.getName())) {
                        author.setBibtex(attr.getStringValue());
                    }
                }
                authors.add(author);
            } else if ("editor".equals(child.getName())) {
                Person editor = new Person();
                editor.setType(1);
                editor.setName(child.getStringValue());
                for (Attribute attr : child.attributes()) {
                    if ("aux".equals(attr.getName())) {
                        editor.setAux(attr.getStringValue());
                    }
                }
                editors.add(editor);
            } else {
                stringBuilder.append(child.getName());
                stringBuilder.append(":\n");
                stringBuilder.append(child.getStringValue());
                stringBuilder.append("\n\n");
            }
        }

        if (authors.size() > 0) {
            publ.setAuthors(authors);
        }
        if (editors.size() > 0) {
            publ.setEditors(editors);
        }
        if (stringBuilder.length() > 0) {
            publ.setDescription(stringBuilder.toString());
        }

        return publ;
    }

    @Transactional
    public void storePublication(Publication publ) {
        publMapper.insert(publ); // must be first to get publ_id

        List<Person> authors = publ.getAuthors();
        List<Person> editors = publ.getEditors();
        List<Person> persons = new ArrayList<>();
        if (authors != null) {
            persons.addAll(authors);
        }
        if (editors != null) {
            persons.addAll(editors);
        }

        for (Person person : persons) {
            Person dbPerson = personMapper.selectByNameAndType(person);
            if (dbPerson == null) {
                personMapper.insert(person);
            } else {
                person.setId(dbPerson.getId());
            }
            PublPerson publPerson = new PublPerson();
            publPerson.setPersonId(person.getId());
            publPerson.setPublId(publ.getId());
            publPersonMapper.insert(publPerson);
        }
    }

    public void storeDblp(String xmlPath) throws DocumentException {
        SAXReader reader = new SAXReader();

        reader.setDefaultHandler(new ElementHandler() {
            private int cnt = 1;
            @Override
            public void onStart(ElementPath path) {

            }

            @Override
            public void onEnd(ElementPath path) {
                Element current = path.getCurrent();
                Element parent = current.getParent();
                if (parent == null || !"dblp".equals(parent.getName())) {
                    //current.detach();
                    return;
                }
                Publication publ = readPublicationFromElement(current);
                storePublication(publ);
                logger.debug(String.valueOf(cnt++));
                current.detach();
            }
        });

        File xmlFile = new File(xmlPath);
        Document doc = reader.read(xmlFile);
    }
}
