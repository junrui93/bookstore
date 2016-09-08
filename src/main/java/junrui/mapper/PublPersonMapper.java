package junrui.mapper;

import java.util.List;
import junrui.model.PublPerson;

public interface PublPersonMapper {
    int insert(PublPerson record);

    List<PublPerson> selectAll();
}