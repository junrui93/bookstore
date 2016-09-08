package junrui.web;

import junrui.logic.SearchLogic;
import junrui.mapper.PublicationMapper;
import junrui.model.Publication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class PageController {

    private static final Logger logger = LoggerFactory.getLogger(PageController.class);

    @Autowired
    private SearchLogic searchLogic;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home() {
        return "home.jsp";
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String info(@RequestParam int id, Map<String, Object> model) {
        id = Math.max(id, 0);
        Publication publication = searchLogic.searchById(id);
        model.put("publ", publication);
        return "info.jsp";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(@RequestParam MultiValueMap<String, String> params, Map<String, Object> model) {
        int page = 1;
        if (params.containsKey("page")) {
            String pageString = params.getFirst("page");
            try {
                page = Integer.parseInt(pageString);
            } catch (NumberFormatException e) {
                logger.warn(e.getMessage());
            }
        }

        if (params.containsKey("keyword")) {
            String keyword = params.getFirst("keyword");
            model.putAll(searchLogic.searchByKeyword(keyword, page));

        } else {
            Map<String, Object> condition = new HashMap<>();
            String[] paramNames = {"title", "author", "venue"};
            for (String paramName : paramNames) {
                if (params.containsKey(paramName) && !params.getFirst(paramName).isEmpty()) {
                    condition.put(paramName, params.getFirst(paramName));
                }
            }
            if (params.containsKey("yearfrom") && !params.getFirst("yearfrom").isEmpty()) {
                try {
                    Integer yearFrom = Integer.parseInt(params.getFirst("yearfrom"));
                    condition.put("yearfrom", yearFrom);
                    Integer yearTo = yearFrom;
                    if (params.containsKey("yearto") && !params.getFirst("yearto").isEmpty()) {
                        try {
                            yearTo = Integer.parseInt(params.getFirst("yearfrom"));
                        } catch (NumberFormatException e) {
                            logger.warn(e.getMessage());
                        }
                    }
                    condition.put("yearto", yearTo);
                } catch (NumberFormatException e) {
                    logger.warn(e.getMessage());
                }
            }
            if (params.containsKey("type") && params.get("type").size() > 0) {
                condition.put("type", params.get("type"));
            }
            model.putAll(searchLogic.searchByCondition(condition, page));
        }
        return "search.jsp";
    }
}
