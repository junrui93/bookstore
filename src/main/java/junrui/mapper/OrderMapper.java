package junrui.mapper;

import java.util.List;
import junrui.model.Order;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    Order selectByPrimaryKey(Integer id);

    List<Order> selectAll();

    int updateByPrimaryKey(Order record);

    Order selectByUserAndPubl(
            @Param("userId") int userId,
            @Param("publicationId") int publicationId);

    int incrementById(Integer id);

    int decrementById(Integer id);

    List<Order> selectByUserId(
            @Param("userId") Integer userId,
            @Param("adminView") Boolean adminView);

    int countByUserId(
            @Param("userId") Integer userId,
            @Param("adminView") Boolean adminView);

    int updateRemoveTs(Integer id);

    int updateCommitTs(Integer id);
}