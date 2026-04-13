package cn.njcit.service;

import cn.njcit.entity.AttendClassSetting;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 课程时间设置 服务类
 * </p>
 *
 * @author selena
 * @since 2023-10-24
 */
public interface IAttendClassSettingService extends IService<AttendClassSetting> {
    List<AttendClassSetting> getAttendClassSettingById(Long id);

    PageInfo<AttendClassSetting> getSettingList(Integer page, String searchName);
}
