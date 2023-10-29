package cn.njcit.service;

import cn.njcit.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 课程信息 服务类
 * </p>
 *
 * @author selena
 * @since 2023-10-24
 */
public interface ICourseService extends IService<Course> {
    /**
     * 查询所有课程信息
     * @param page:页号
     * @return PageInfo对象
     */
    public PageInfo<Course> getAllCourseList(Integer page, String searchName);

    /**
     * 按照id查询课程
     * @param id : 学生的用户id
     * @return : 学生对象
     */
    public Course getCourseById(Long id);
    /**
     * 按照id查询课程，删除
     * @param id:课程id
     * @return:
     */
    public boolean deleteCourse(Long id);
}
