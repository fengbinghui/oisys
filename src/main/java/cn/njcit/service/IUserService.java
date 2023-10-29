package cn.njcit.service;

import cn.njcit.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author selena
 * @since 2023-10-24
 */
public interface IUserService extends IService<User> {
    /**
     * 查询所有学生/核实名搜索学生，searchName为null,查询所有学生信息；searchName不为null，按照姓名模糊查询学生
     *
     * @param page:页号
     * @param searchName:搜索实名（支持模糊查询）
     * @return PageInfo对象
     */
    public PageInfo<User> getStudentList(Integer page, String searchName);

    /**
     * 按照id查询学生id
     *
     * @param id:学生的用户id
     * @return: 学生对象
     */
    public User getStudentById(Long id);

    /**
     * 上传学生头像
     *
     * @param file:头像文件
     * @throws IllegalStateException
     * @throws IOException
     * @return：上传成功后的头像文文件名
     */
    public String upload(MultipartFile file) throws IllegalStateException, IOException;
}