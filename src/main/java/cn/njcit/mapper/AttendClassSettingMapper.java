package cn.njcit.mapper;

import cn.njcit.entity.AttendClassSetting;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程时间设置 Mapper 接口
 * </p>
 *
 * @author selena
 * @since 2023-10-24
 */
public interface AttendClassSettingMapper extends BaseMapper<AttendClassSetting> {
    List<AttendClassSetting> getAttendClassSettingById(Long id);

    List<AttendClassSetting> getAllSettings();

    List<AttendClassSetting> searchSettings(@Param("searchName") String searchName);
}
