package cn.njcit.service.impl;

import cn.njcit.entity.AttendsList;
import cn.njcit.mapper.AttendsListMapper;
import cn.njcit.service.IAttendsListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 考勤打卡 服务实现类
 * </p>
 *
 * @author selena
 * @since 2023-10-24
 */
@Service
public class AttendsListServiceImpl extends ServiceImpl<AttendsListMapper, AttendsList> implements IAttendsListService {
    private final int PAGE_SIZE = 6;// 每页展示的条数

    @Autowired
    private AttendsListMapper attendsListMapper;
    @Override
    public PageInfo<AttendsList> getAttendsList(Integer page, String searchDate) {
        PageHelper.startPage(page, PAGE_SIZE);
        List<AttendsList> attendsList = attendsListMapper.getAttendByDate(searchDate);
        return new PageInfo<>(attendsList);
    }

    @Override
    public PageInfo<AttendsList> getAttendanceDetails(Integer page, String userName, String searchDate) {
        PageHelper.startPage(page, PAGE_SIZE);
        List<AttendsList> detailList = attendsListMapper.getAttendanceDetails(userName, searchDate);
        return new PageInfo<>(detailList);
    }
}
