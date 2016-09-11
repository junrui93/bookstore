package junrui.mapper;

import java.util.List;
import junrui.model.Verification;
import org.apache.ibatis.annotations.Param;

public interface VerificationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Verification record);

    Verification selectByPrimaryKey(Integer id);

    List<Verification> selectAll();

    int updateByPrimaryKey(Verification record);

    Verification selectByUserIdAndCode(
            @Param("userId") int userId,
            @Param("code") String code);
}