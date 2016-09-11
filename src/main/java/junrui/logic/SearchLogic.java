package junrui.logic;

import junrui.mapper.PersonMapper;
import junrui.mapper.PublicationMapper;
import junrui.model.Publication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SearchLogic {

    private static final Logger logger = LoggerFactory.getLogger(SearchLogic.class);
    private static final int PAGE_SIZE = 10;

    @Autowired
    private PublicationMapper publMapper;

    @Autowired
    private PersonMapper personMapper;

    public Map<String, Object> searchByKeyword(String keyword, int page, Boolean removed) {
        Map<String, Object> map = Utils.calculatePages(publMapper.countByKeyword(keyword, removed), page, PAGE_SIZE);

        page = (Integer) map.get("page");
        int offset = (page - 1) * PAGE_SIZE;
        List<Publication> publications = publMapper.selectByKeyword(keyword, removed, offset, PAGE_SIZE);
        for (Publication publ : publications) {
            logger.debug(String.format("%d: %s", publ.getId(), publ.getTitle()));
        }
        //fillAuthors(publications);
        map.put("publications", publications);
        return map;
    }

    public Map<String, Object> searchByCondition(Map<String, Object> condition, int page) {
        Map<String, Object> map = Utils.calculatePages(publMapper.countByCondition(condition), page, PAGE_SIZE);

        page = (Integer) map.get("page");
        int offset = (page - 1) * PAGE_SIZE;
        List<Publication> publications = publMapper.selectByCondition(condition, offset, PAGE_SIZE);
        //fillAuthors(publications);
        map.put("publications", publications);

        return map;
    }

    public Publication searchById(int id) {
        Publication publication = publMapper.selectByPrimaryKey(id);
        if (publication != null) {
            publication.setAuthors(personMapper.selectByPublicationId(publication.getId(), 0));
        }
        return publication;
    }

    public Map<String, Object> searchBySellerId(int sellerId, boolean removed, int page) {
        Map<String, Object> map = Utils.calculatePages(publMapper.countBySellerId(sellerId, removed), page, PAGE_SIZE);
        page = (Integer) map.get("page");
        int offset = (page - 1) * PAGE_SIZE;
        List<Publication> publications = publMapper.selectBySellerId(sellerId, removed, offset, PAGE_SIZE);
        map.put("publications", publications);
        return map;
    }
}
