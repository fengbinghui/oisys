package cn.njcit.service.impl;

import cn.njcit.entity.Course;
import cn.njcit.mapper.CourseMapper;
import cn.njcit.service.ICourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程信息 服务实现类
 * </p>
 *
 * @author selena
 * @since 2023-10-24
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {
    private final int PAGE_SIZE = 5;// 每页展示的条数

    /**
     * 查询所有课程信息
     *
     * @param page:页号
     * @return: 包装了课程列表和分页信息的分页对象
     */
    @Override
    public PageInfo<Course> getAllCourseList(Integer page, String searchName) {
        PageHelper.startPage(page, PAGE_SIZE);// 设置起始页，次此返回第几页
        List<Course> courseList = null;
        if (searchName == null || searchName.trim().equals("")) {// searchName为null或者空字符串，查询所有课程
            courseList = baseMapper.getAllCourses();// 注意此处的baseMapper，已经注入，可以直接用
        } else {// searchName不为null，按照课程名称查询课程
            courseList = baseMapper.searchCourses(searchName);
        }
        return new PageInfo<Course>(courseList);// 将查询到的课程列表包装成分页对象
    }

    @Override
    public Course getCourseById(Long id) {
        Course course = baseMapper.getCourseById(id);
        return course;
    }

    /**
     * @param id:课程id
     * @return
     */
    @Override
    public boolean deleteCourse(Long id) {
        return baseMapper.deleteCourse(id);
    }
}
