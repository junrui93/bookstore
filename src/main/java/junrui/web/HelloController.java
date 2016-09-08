package junrui.web;

import junrui.mapper.PersonMapper;
import junrui.mapper.UserMapper;
import junrui.model.Person;
import junrui.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class HelloController {

    private final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private PersonMapper personMapper;

    @RequestMapping("/")
    public String anyFunctionName(Map<String, Object> model) {
        logger.debug("in anyFunctionName");
        Person person = personMapper.selectByPrimaryKey(1);
        model.put("message", person.getName()); //test Person
        return "hello.jsp"; // view name
    }

    @RequestMapping("/hello/{name}")
    public ModelAndView anyFunctionName2(@PathVariable String name) {
        logger.debug("in anyFunctionName2");
        ModelAndView model = new ModelAndView();
        model.setViewName("hello.jsp");
        model.addObject("message", String.format("Hello, %s!", name));
        return model;
    }
}
