package junrui.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import junrui.model.Entity;
import junrui.model.Person;
import junrui.model.Publication;
import junrui.model.Venue;

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
    }

    /*@Test
    public void testExample() {
    	List<Venue> venue = entityMapper.selectVenues(); 	
        logger.debug(persons.get(0).getType().toString());
        
    	for(Venue v : venue)
    	{
    		Entity entity = new Entity();	
    		entity.setVenueEntityId(v.getId());  			
    		entity.setEntityAttribute("Type");
    		entity.setAttributeValue("Venue");
    		entityMapper.insertVenue(entity);
    		
    		Entity entity2 = new Entity();	
    		entity2.setVenueEntityId(v.getId());  	
    		entity2.setEntityAttribute("Class");
    		entity2.setAttributeValue("entityNode");  		
    		entityMapper.insertVenue(entity2);
    		
    		Entity entity3 = new Entity();	
    		entity3.setVenueEntityId(v.getId());  	
    		entity3.setEntityAttribute("Name");
    		entity3.setAttributeValue(v.getName());  		
    		entityMapper.insertVenue(entity3);
    	}
    }*/
}
