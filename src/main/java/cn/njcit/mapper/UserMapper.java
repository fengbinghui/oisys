package cn.njcit.mapper;

import cn.njcit.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author selena
 * @since 2023-10-24
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     * 查询所有学生
     * @return：学生列表
     */
    public List<User> getAllStudents();
    /**
     * 按照实名搜索学生（模糊查询）
     * @return：学生列表
     */
    public List<User> searchStudents(String searchName);
    /**
     * 按照id查询学生
     * @param id：学生的用户id
     * @return：学生对象
     */
    public User getStudentById(Long id);


}
