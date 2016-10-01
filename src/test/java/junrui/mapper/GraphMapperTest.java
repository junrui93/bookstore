package junrui.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junrui.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring-root-config.xml"})
public class GraphMapperTest {

    private Logger logger = LoggerFactory.getLogger(GraphMapperTest.class);

    @Autowired
    private GraphEntityMapper entityMapper;

    @Autowired
    private GraphRelationMapper relationMapper;

    @Autowired
    private PublicationMapper publicationMapper;

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private VenueMapper venueMapper;

    @Test
    public void testEmpty() {}

    // @Test
    public void testInsertEntity() {
        List<Publication> publications = publicationMapper.selectAll();
        List<Person> authors = personMapper.selectByType(0);
        List<Venue> venues = venueMapper.selectAll();

        int entityId = 1;

        Map<Integer, Person> authorMap = new HashMap<>();
        Map<Integer, Venue> venueMap = new HashMap<>();

        for (Person author : authors) {
            author.setEntityId(entityId++);
            authorMap.put(author.getId(), author);

            entityMapper.insert(makeEntity(author.getEntityId(), "class", "author"));
            entityMapper.insert(makeEntity(author.getEntityId(), "name", author.getName()));
            entityMapper.insert(makeEntity(author.getEntityId(), "origin_id", author.getId().toString()));
        }

        for (Venue venue : venues) {
            venue.setEntityId(entityId++);
            venueMap.put(venue.getId(), venue);

            entityMapper.insert(makeEntity(venue.getEntityId(), "class", "venue"));
            entityMapper.insert(makeEntity(venue.getEntityId(), "name", venue.getName()));
            entityMapper.insert(makeEntity(venue.getEntityId(), "origin_id", venue.getId().toString()));
        }

        for (Publication publication : publications) {
            publication.setEntityId(entityId++);

            List<Person> relateAuthors = personMapper.selectByPublicationId(publication.getId(), 0);
            for (Person author : relateAuthors) {
                int authorId = author.getId();
                Person author2 = authorMap.get(authorId);
                int authorEntityId = author2.getEntityId();
                GraphRelation relation = new GraphRelation();
                relation.setSubjectId(publication.getEntityId());
                relation.setObjectId(authorEntityId);
                relation.setEdgeId(entityId);
                relationMapper.insert(relation);
                entityMapper.insert(makeEntity(entityId, "class", "edge"));
                entityMapper.insert(makeEntity(entityId++, "label", "authoredBy"));
            }

            Integer venueId = publication.getVenueId();
            if (venueId != null) {
                GraphRelation relation = new GraphRelation();
                relation.setSubjectId(publication.getEntityId());
                relation.setObjectId(venueMap.get(venueId).getEntityId());
                relation.setEdgeId(entityId);
                relationMapper.insert(relation);
                entityMapper.insert(makeEntity(entityId, "class", "edge"));
                entityMapper.insert(makeEntity(entityId++, "label", "publishedIn"));
            }

            entityMapper.insert(makeEntity(publication.getEntityId(), "origin_id", publication.getId().toString()));
            entityMapper.insert(makeEntity(publication.getEntityId(), "class", "publication"));
            entityMapper.insert(makeEntity(publication.getEntityId(), "title", publication.getTitle()));
            entityMapper.insert(makeEntity(publication.getEntityId(), "type", publication.getType()));
            if (publication.getYear() != null) {
                entityMapper.insert(makeEntity(publication.getEntityId(), "year", publication.getYear().toString()));
            }
            if (publication.getDescription() != null) {
                entityMapper.insert(makeEntity(publication.getEntityId(), "description", publication.getDescription()));
            }
            if (publication.getPrice() != null) {
                entityMapper.insert(makeEntity(publication.getEntityId(), "price", publication.getPrice().toString()));
            }

            logger.debug("Convert to Graph, Publication Id", publication.getId());
        }
    }

    private GraphEntity makeEntity(int entityId, String key, String value) {
        GraphEntity entity = new GraphEntity();
        entity.setEntityId(entityId);
        entity.setKey(key);
        entity.setValue(value);
        return entity;
    }
}
