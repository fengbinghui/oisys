package cn.njcit.service;

import cn.njcit.entity.CourseManage;
import cn.njcit.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 课程管理表 服务类
 * </p>
 *
 * @author selena
 * @since 2023-10-24
 */
public interface ICourseManageService extends IService<CourseManage> {
    //查询所有课程安排信息
    public PageInfo<CourseManage> getCourseManageList(Integer page, String searchName);
    //查询所有老师
    public List<User> getAllTeachers();
    //通过id查询课程安排
    public List<CourseManage> getCourseManageById(Long id);
}
