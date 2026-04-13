package cn.njcit.service.impl;

import cn.njcit.entity.UserLoginRecord;
import cn.njcit.mapper.UserLoginRecordMapper;
import cn.njcit.service.IUserLoginRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author selena
 * @since 2023-10-24
 */
@Service
public class UserLoginRecordServiceImpl extends ServiceImpl<UserLoginRecordMapper, UserLoginRecord> implements IUserLoginRecordService {
    private static final int PAGE_SIZE = 10;

    @Override
    public PageInfo<UserLoginRecord> getLoginRecordList(Integer page, String searchName) {
        PageHelper.startPage(page, PAGE_SIZE);
        List<UserLoginRecord> rows;
        if (searchName == null || searchName.trim().isEmpty()) {
            rows = baseMapper.selectList(null);
        } else {
            rows = baseMapper.searchLoginRecords(searchName);
        }
        return new PageInfo<>(rows);
    }
}
