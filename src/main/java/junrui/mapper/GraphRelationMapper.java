package junrui.mapper;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import junrui.model.GraphRelation;

public interface GraphRelationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GraphRelation record);

    GraphRelation selectByPrimaryKey(Integer id);

    List<GraphRelation> selectAll();

    int updateByPrimaryKey(GraphRelation record);

    List<GraphRelation> selectByEntityIds(Collection<Integer> entityIds);
}