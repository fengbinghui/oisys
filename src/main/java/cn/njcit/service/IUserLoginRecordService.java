package cn.njcit.service;

import cn.njcit.entity.UserLoginRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author selena
 * @since 2023-10-24
 */
public interface IUserLoginRecordService extends IService<UserLoginRecord> {
    PageInfo<UserLoginRecord> getLoginRecordList(Integer page, String searchName);
}
