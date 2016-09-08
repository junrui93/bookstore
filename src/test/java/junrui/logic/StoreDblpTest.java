package junrui.logic;

import junrui.model.Publication;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring-root-config.xml"})
public class StoreDblpTest {

    @Autowired
    private StoreDblp storeDblp;

    private Document createDocument() {
        Document document = DocumentHelper.createDocument();
        Element article = document.addElement( "article" );

        article.addElement( "author" ).addText( "Junrui Chen" );
        article.addElement( "author" ).addText( "Siming Bai" );
        article.addElement( "title" ).addText( "Computer Science and Technology" );
        article.addElement( "year" ).addText( "2016" );
        article.addElement( "booktitle" ).addText( "CVPR" );

        return document;
    }

    @Test
    public void testReadPublication() {
        Publication publ = storeDblp.readPublicationFromElement(createDocument().getRootElement());
        assert publ.getTypeId() == 0;
        assert publ.getTitle().equals( "Computer Science and Technology" );
        assert publ.getYear() == 2016;
        assert publ.getVenue().equals( "CVPR" );
        assert publ.getAuthors().get(0).getName().equals( "Junrui Chen" );
    }

//    @Test
//    public void testStoreDblp() throws DocumentException {
//        storeDblp.storeDblp("/Users/junrui/code/java/DigitalLibrary/WebContent/WEB-INF/dblp_sample.xml");
//    }
}
