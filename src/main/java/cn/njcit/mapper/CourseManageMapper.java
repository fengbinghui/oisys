package cn.njcit.mapper;

import cn.njcit.entity.CourseManage;
import cn.njcit.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 课程管理表 Mapper 接口
 * </p>
 *
 * @author selena
 * @since 2023-10-24
 */
public interface CourseManageMapper extends BaseMapper<CourseManage> {
    // 查询所有老师
    public List<User> getAllTeachers();

    // 通过id查询课程安排
    public List<CourseManage> getCourseManageById(Long id);

    // 按照课程名搜索
    public List<CourseManage> getCourseManageByName(String searchName);

    // 查询所有课程安排信息
    public List<CourseManage> getAllCourseManage();
}

