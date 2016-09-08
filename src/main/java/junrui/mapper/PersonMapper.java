package junrui.mapper;

import java.util.List;
import junrui.model.Person;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface PersonMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Person record);

    Person selectByPrimaryKey(Integer id);

    List<Person> selectAll();

    int updateByPrimaryKey(Person record);

    @Select("select id, name, aux, bibtex from person where name=#{name} and type=#{type} limit 1")
    Person selectByNameAndType(Person record);

    List<Person> selectByPublicationId(
            @Param("publicationId") int publicationId,
            @Param("type") Integer type);
}