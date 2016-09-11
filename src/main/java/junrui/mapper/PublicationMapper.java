package junrui.mapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import junrui.model.Publication;
import org.apache.ibatis.annotations.Param;

public interface PublicationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Publication record);

    Publication selectByPrimaryKey(Integer id);

    List<Publication> selectAll();

    int updateByPrimaryKey(Publication record);

    List<Publication> selectByKeyword(
            @Param("keyword") String keyword,
            @Param("removed") Boolean removed,
            @Param("offset") int offset,
            @Param("limit") int limit);

    int countByKeyword(
            @Param("keyword") String keyword,
            @Param("removed") Boolean removed);

    List<Publication> selectByCondition(
            @Param("condition") Map<String, Object> condition,
            @Param("offset") int offset,
            @Param("limit") int limit);

    int countByCondition(@Param("condition") Map<String, Object> condition);

    List<Publication> selectBySellerId(
            @Param("sellerId") int sellerId,
            @Param("removed") boolean removed,
            @Param("offset") int offset,
            @Param("limit") int limit);

    int countBySellerId(
            @Param("sellerId") int sellerId,
            @Param("removed") boolean removed);

    int updateRemovedById(
            @Param("id") int id,
            @Param("removed") boolean removed);

    List<Integer> selectAllIds();

    List<Publication> selectByIds(Collection<Integer> ids);
}