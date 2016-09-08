package junrui.mapper;

import java.util.List;
import junrui.model.Publication;

public interface PublicationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Publication record);

    Publication selectByPrimaryKey(Integer id);

    List<Publication> selectAll();

    int updateByPrimaryKey(Publication record);
}