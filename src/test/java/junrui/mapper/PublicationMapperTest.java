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

    @Test
    public void testExample() 
    {   		
    	String keyword = "Risk";
    	List<Graph> resultList = entityMapper.graphSearch(keyword);
    	for(Graph g : resultList){
    		if(g.getNodeFrom().startsWith("P")){
    			String title = entityMapper.selectTitle(g.getNodeFrom());
    			System.out.println(title);
    		}
    		
    	}
    	
    			
    	//logger.debug(g.getNodeFrom()+"-"+g.getNodeEdge()+"-"+g.getNodeTo());
    	
    }
    
    
    
    
}
