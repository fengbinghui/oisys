package cn.njcit.service.impl;

import cn.njcit.entity.AttendClassSetting;
import cn.njcit.mapper.AttendClassSettingMapper;
import cn.njcit.service.IAttendClassSettingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程时间设置 服务实现类
 * </p>
 *
 * @author selena
 * @since 2023-10-24
 */
@Service
public class AttendClassSettingServiceImpl extends ServiceImpl<AttendClassSettingMapper, AttendClassSetting> implements IAttendClassSettingService {
    private static final int PAGE_SIZE = 10;

    @Override
    public List<AttendClassSetting> getAttendClassSettingById(Long id) {
        return baseMapper.getAttendClassSettingById(id);
    }

    @Override
    public PageInfo<AttendClassSetting> getSettingList(Integer page, String searchName) {
        PageHelper.startPage(page, PAGE_SIZE);
        List<AttendClassSetting> list;
        if (searchName == null || searchName.trim().isEmpty()) {
            list = baseMapper.getAllSettings();
        } else {
            list = baseMapper.searchSettings(searchName);
        }
        return new PageInfo<>(list);
    }
}
