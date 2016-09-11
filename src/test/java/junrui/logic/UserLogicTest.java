package junrui.logic;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UserLogicTest {

    private Logger logger = LoggerFactory.getLogger(UserLogicTest.class);

    @Test
    public void testSendEmail() {
        //UserLogic.sendEmail("285589385@qq.com", "hello 2", "another test email");
    }

    @Test
    public void testURLEncode() throws UnsupportedEncodingException {
        logger.debug(URLEncoder.encode("/static/Screen Shot 2016-09-05 at 9.15.32 PM.png", "UTF-8"));
    }
}
