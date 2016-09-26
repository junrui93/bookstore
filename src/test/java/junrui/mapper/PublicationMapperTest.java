package junrui.mapper;

import junrui.model.Publication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import junrui.model.Entity;
import junrui.model.Publication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring-root-config.xml"})
public class PublicationMapperTest {

    private Logger logger = LoggerFactory.getLogger(PublicationMapperTest.class);

    @Autowired
    private PublicationMapper publMapper;
    
    @Autowired
    private EntityMapper entityMapper;

    @Test
    public void testSelect() {
        //publMapper.countByKeyword("CVPR");
        //publMapper.selectByKeyword("CVPR", 0, 10);
        
    	//Map<String, Object> condition = new HashMap<>();
        //condition.put("title", "convolutional neural network");
        //List<String> types = new ArrayList<>();
        //types.add("article");
    	
        //condition.put("type", types); 	
        //publMapper.selectByCondition(condition, 0, 10);
    	
    	List<Publication> publications = entityMapper.selectedAttributes();
    	
    	//List<Publication> tempPubl = new LinkedList<>();
    	//tempPubl.addAll(publications);
    	
    	//for(Publication pub : publications) {
    	
    	
    		Entity entity = new Entity();	
    		entity.setPublEntityId(publications.get(0).getId());
    		//entity.setPublEntityAttribute("Title");
    		//entity.setPublAttributeValue(pub.getTitle());
    		//entity.setPublEntityAttribute("Type");
    		//entity.setPublAttributeValue(publications.get(0).getPublTypeName());
    		
    		//entityMapper.insert(entity);
    	//}
    	
    	System.out.println(publications.toString());
    }

    @Test
    public void testExample() {
        Publication publication = publMapper.selectExample();
        logger.debug(publication.getType());
    }
}
