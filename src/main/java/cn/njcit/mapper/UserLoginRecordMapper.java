package cn.njcit.mapper;

import cn.njcit.entity.UserLoginRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author selena
 * @since 2023-10-24
 */
public interface UserLoginRecordMapper extends BaseMapper<UserLoginRecord> {
    List<UserLoginRecord> searchLoginRecords(@Param("searchName") String searchName);
}
