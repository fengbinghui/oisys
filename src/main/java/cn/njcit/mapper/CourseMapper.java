package cn.njcit.mapper;

import cn.njcit.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 课程信息 Mapper 接口
 * </p>
 *
 * @author selena
 * @since 2023-10-24
 */
public interface CourseMapper extends BaseMapper<Course> {
    /**
     * 查询所有课程
     * return: 课程列表
     */
    public List<Course> getAllCourses();

    /**
     * 模糊查询课程
     */
    public List<Course> searchCourses(String searchName);

    /**
     * 按照id查询课程
     * return: 课程对象
     */
    public Course getCourseById(Long id);

    /**
     * 根据id删除课程
     *
     * @param id:课程的id
     * @return
     */
    public boolean deleteCourse(Long id);
}
