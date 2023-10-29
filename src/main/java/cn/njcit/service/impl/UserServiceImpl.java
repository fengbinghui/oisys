package cn.njcit.service.impl;

import cn.njcit.entity.User;
import cn.njcit.mapper.UserMapper;
import cn.njcit.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author selena
 * @since 2023-10-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    private final int PAGE_SIZE = 5;// 每页展示的条数
    @Value("${userImageRoot}")
    private String userImageRoot;// 注入学生头像上传的根目录

    /**
     * 查询所有/按实名搜索学生，searchName为null，查询所有学生信息；searchName不为null，按照姓名模糊查询学生，均返回第page页
     *
     * @param page:第几页
     * @param searchName：搜索实名（支持模糊查询）
     * @return：包装了学生列表和分页信息的分页对象。
     */
    @Override
    public PageInfo<User> getStudentList(Integer page, String searchName) {
        PageHelper.startPage(page, PAGE_SIZE);// 设置起始页，次此返回第几页
        List<User> studentList = null;
        if (searchName == null || searchName.trim().equals("")) {// searchName为null或者空字符串，查询所有学生
            studentList = baseMapper.getAllStudents();// 注意此处的baseMapper就是UserMapper,已经注入，可以直接用
        } else {// searchName不为null，按照实名查询学生
            studentList = baseMapper.searchStudents(searchName);
        }
        return new PageInfo<User>(studentList);// 将查询到的学生列表包装成分页对象
    }

    /**
     * 按照id查询学生
     *
     * @param id：学生id
     * @return：学生对象
     */
    @Override
    public User getStudentById(Long id) {
        User student = baseMapper.getStudentById(id);
        return student;
    }

    /**
     * 上传的文件不是null，文件名也不是null，上传图片到指定目录，返回生成的图片名。否则返回null
     *
     * @param file: 上传文件
     * @throws IOException
     * @throws IllegalStateException
     * @return: 上传后的文件名
     */
    public String upload(MultipartFile file) throws IllegalStateException, IOException {
        if (file == null) {
            return null;// 文件是空（用户没有选择头像）返回null
        }
        File dir = new File(userImageRoot);
        if (!dir.exists()) {// 检查存放头像的根目录是否存在，不存在就创建
            dir.mkdirs();
        }
        String fileName = file.getOriginalFilename();// 用户上传图片的原始文件名
        if (!StringUtil.isEmpty(fileName)) {// 原始文件名不是空
            String suffix = FilenameUtils.getExtension(fileName);// 获得文件扩展名
            // 产生随机文件名，拼接图片扩展名作为上传后的文件名
            String newFileName = UUID.randomUUID().toString().toLowerCase() + "." + suffix;
            File targetFile = new File(dir, newFileName);// 创建目标文件对象，路径是：存放头像的根目录/上一步生成的文件名
            file.transferTo(targetFile);// 将用户选择的头像上传到目标文件
            return newFileName;// 返回上传后的文件名
        } else {
            return null;
        }
    }


}
