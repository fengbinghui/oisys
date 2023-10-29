package cn.njcit.service.impl;

import cn.njcit.entity.CourseManage;
import cn.njcit.entity.User;
import cn.njcit.mapper.CourseManageMapper;
import cn.njcit.service.ICourseManageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程管理表 服务实现类
 * </p>
 *
 * @author selena
 * @since 2023-10-24
 */
@Service
public class CourseManageServiceImpl extends ServiceImpl<CourseManageMapper, CourseManage> implements ICourseManageService {
    private final int PAGE_SIZE = 6;// 每页展示的条数

    @Override
    public PageInfo<CourseManage> getCourseManageList(Integer page, String searchName) {
        PageHelper.startPage(page, PAGE_SIZE);// 设置起始页，次此返回第几页
        List<CourseManage> courseManageList = null;// 查找方法
        if (searchName == null || searchName.trim().equals("")) {
            courseManageList = baseMapper.getAllCourseManage();
        } else {
            courseManageList = baseMapper.getCourseManageByName(searchName);
        }
        return new PageInfo<CourseManage>(courseManageList);
    }

    @Override
    public List<User> getAllTeachers() {
        List<User> teachers = baseMapper.getAllTeachers();
        return teachers;
    }

    @Override
    public List<CourseManage> getCourseManageById(Long id) {
        List<CourseManage> courseManageById = baseMapper.getCourseManageById(id);
        return courseManageById;
    }

}
