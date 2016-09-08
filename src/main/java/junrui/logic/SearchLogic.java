package junrui.logic;

import junrui.mapper.PersonMapper;
import junrui.mapper.PublicationMapper;
import junrui.model.Publication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SearchLogic {

    private static final int PAGE_SIZE = 10;

    @Autowired
    private PublicationMapper publMapper;

    @Autowired
    private PersonMapper personMapper;

    public Map<String, Object> searchByKeyword(String keyword, int page) {
        Map<String, Object> map = calculatePages(publMapper.countByKeyword(keyword), page);

        int offset = (page - 1) * PAGE_SIZE;
        List<Publication> publications = publMapper.selectByKeyword(keyword, offset, PAGE_SIZE);
        fillAuthors(publications);
        map.put("publications", publications);
        return map;
    }

    public Map<String, Object> searchByCondition(Map<String, Object> condition, int page) {
        Map<String, Object> map = calculatePages(publMapper.countByCondition(condition), page);

        int offset = (page - 1) * PAGE_SIZE;
        List<Publication> publications = publMapper.selectByCondition(condition, offset, PAGE_SIZE);
        fillAuthors(publications);
        map.put("publications", publications);

        return map;
    }

    private Map<String, Object> calculatePages(int resultSize, int page) {
        Map<String, Object> map = new HashMap<>();

        int maxPage = (resultSize + PAGE_SIZE - 1) / 10;
        page = Math.min(page, maxPage);
        page = Math.max(page, 1);
        int leftPage = Math.max(page - PAGE_SIZE / 2, 1);
        int rightPage = Math.min(leftPage + PAGE_SIZE - 1, maxPage);

        map.put("resultSize", resultSize);
        map.put("maxPage", maxPage);
        map.put("page", page);
        map.put("leftPage", leftPage);
        map.put("rightPage", rightPage);

        return map;
    }

    private void fillAuthors(List<Publication> publications) {
        // TODO reduce db access times
        for (Publication publication : publications) {
            publication.setAuthors(personMapper.selectByPublicationId(publication.getId(), 0));
        }
    }
}
