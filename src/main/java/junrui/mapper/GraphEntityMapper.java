package junrui.mapper;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import junrui.model.GraphEntity;
import org.apache.ibatis.annotations.Param;

public interface GraphEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GraphEntity record);

    GraphEntity selectByPrimaryKey(Integer id);

    List<GraphEntity> selectAll();

    int updateByPrimaryKey(GraphEntity record);

    Set<Integer> selectByClassAndValue(
            @Param("className") String className,
            @Param("key") String key,
            @Param("value") String value);

    List<GraphEntity> selectByEntityIds(Collection<Integer> entityIds);
}