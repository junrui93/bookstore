package junrui.mapper;

import junrui.model.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring-root-config.xml"})
public class PersonMapperTest {

    private static final Logger logger = LoggerFactory.getLogger(PersonMapperTest.class);

    @Autowired
    private PersonMapper personMapper;

    @Test
    public void testPersonMapperSelect() {
        Person author = new Person();
        author.setName("Jian Li");
        author.setType(0);
        Person person = personMapper.selectByNameAndType(author);
        assert person != null;
    }
//
//    @Test
//    public void testPersonMapperInsert() {
//        Person person = new Person();
//        person.setName("chenjr");
//        person.setType(0);
//        int id = personMapper.insert(person);
//        logger.debug(String.valueOf(person.getId()));
//    }
}
