package junrui.mapper;

import java.util.List;
import junrui.model.Venue;

public interface VenueMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Venue record);

    Venue selectByPrimaryKey(Integer id);

    List<Venue> selectAll();

    int updateByPrimaryKey(Venue record);

    Venue selectByName(String name);
}