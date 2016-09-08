package junrui.mapper;

import java.util.List;
import junrui.model.PublType;

public interface PublTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PublType record);

    PublType selectByPrimaryKey(Integer id);

    List<PublType> selectAll();

    int updateByPrimaryKey(PublType record);
}