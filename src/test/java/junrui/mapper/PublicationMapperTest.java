package junrui.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring-root-config.xml"})
public class PublicationMapperTest {

    @Autowired
    private PublicationMapper publMapper;

    @Test
    public void testSelect() {
        publMapper.countByKeyword("CVPR");
        publMapper.selectByKeyword("CVPR", 0, 10);
        Map<String, Object> condition = new HashMap<>();
        condition.put("title", "convolutional neural network");
        List<String> types = new ArrayList<>();
        types.add("article");
        //condition.put("type", types);
        publMapper.selectByCondition(condition, 0, 10);
    }
}
