package cn.njcit.service;

import cn.njcit.entity.AttendsList;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 考勤打卡 服务类
 * </p>
 *
 * @author selena
 * @since 2023-10-24
 */
public interface IAttendsListService extends IService<AttendsList> {
    public PageInfo<AttendsList> getAttendsList(Integer page, String searchName);

    public PageInfo<AttendsList> getAttendanceDetails(Integer page, String userName, String searchDate);
}
