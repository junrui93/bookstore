package junrui.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import junrui.model.Entity;
import junrui.model.Graph;
import junrui.model.PublPerson;
import junrui.model.Publication;
import junrui.model.Venue;

import java.util.ArrayList;
import java.util.HashMap;
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

    @Test
    public void testExample() {
    	List<PublPerson> links = entityMapper.selectLink(); 	
        //logger.debug(links.get(0).getPersonId()+" | "+links.get(0).getAuthor());
        //logger.debug(links.get(1).getPersonId()+" | "+links.get(1).getAuthor());
    	
    	Graph g = new Graph();
    	
    	for(PublPerson pp : links)
    	{
    		Entity entry1 = new Entity();	
    		entry1.setEdgeEntityId(pp.getPersonId());  			
    		entry1.setEntityAttribute("Type");
    		entry1.setAttributeValue("directedLink");
    		entityMapper.insertEdge(entry1);
    		
    		Entity entry2 = new Entity();	
    		entry2.setEdgeEntityId(pp.getPersonId());  	
    		entry2.setEntityAttribute("Class");
    		entry2.setAttributeValue("Edge");  		
    		entityMapper.insertEdge(entry2);
    		
    		Entity entry3 = new Entity();	
    		entry3.setEdgeEntityId(pp.getPersonId());  	
    		entry3.setEntityAttribute("Label");
    		entry3.setAttributeValue("authoredBy");  		
    		entityMapper.insertEdge(entry3);
    		
    		g.setNodeFrom("P"+pp.getPublId()); 		
    		g.setNodeEdge("E"+pp.getPersonId());		
    		g.setNodeTo("A"+pp.getAuthor());
    		entityMapper.insertGraph(g);
    		
    	}
    	
    }
}
