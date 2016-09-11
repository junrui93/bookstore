package junrui.mapper;

import java.util.List;
import junrui.model.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    User selectByUsername(String username);

    List<User> selectByType(int type);

    int updateBannedById(
            @Param("userId") int userId,
            @Param("banned") boolean banned);
}