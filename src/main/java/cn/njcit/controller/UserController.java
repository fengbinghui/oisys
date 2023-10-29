package cn.njcit.controller;

import cn.njcit.entity.OIClass;
import cn.njcit.entity.User;
import cn.njcit.service.IClassService;
import cn.njcit.service.IUserService;
import cn.njcit.util.ResponseResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author selena
 * @since 2023-10-24
 */
@RestController
@RequestMapping("/studentManage")
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IClassService classService;

    /**
     * 指定页号和学生实名，查询学生，返回包装了学生列表和分页信息的分页对象，如果searchName为null，查询所有学生。
     *
     * @param page:       页号
     * @param searchName: 学生实名，可能为null
     * @return 响应消息携带学生列表
     */
    @GetMapping("/list")
    public ResponseResult studentManage(@RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(required = false) String searchName) {
        PageInfo<User> studentList = userService.getStudentList(page, searchName);
        return ResponseResult.ok().put("studentList", studentList);
    }

    /**
     * 新增或者修改学生信息第一步。如果是新增学生，返回班级列表；如果是修改学生信息，返回按照id查询到的学生信息和班级列表。
     * 班级列表用于在新增/修改页面中提供用户选择班级
     *
     * @param userId: 学生的用户id，可能为null，如果为null则是新增学生
     * @return: ResponseResult(" student ", 学生对象), (" classes ", 班级列表)
     */
    @GetMapping("/edit")
    public ResponseResult studentEdit(@RequestParam(required = false) Long userId) {
        ResponseResult responseResult = ResponseResult.ok();
        if (userId != null) {// userId不是空，表示要修改学生信息，返回该学生对象
            User user = userService.getStudentById(userId);
            responseResult.put("student", user);// 学生对象加入responseResult
        }
        List<OIClass> classes = classService.list();// 班级列表对象
        responseResult.put("classes", classes);// 班级列表加入responseResult
        return responseResult;
    }

    /**
     * insert/update学生信息，如果user对象中的用户id是空，表示新增学生，
     *
     * @param user          ; 学生对象
     * @param filePath      ; 上传头像
     * @param resetPassword ; 是否重置密码
     * @return : 用户名重复 (新增时) : error(用户名被占用), 正常新增或更新 ; ok("保存成功")，新增或更新出错: error(保存失败)
     * @throws IOException
     * @throws InterruptedException
     * @throws IOException
     */
    @PostMapping("/save")
    public ResponseResult saveStudent(User user, @RequestParam(required = false) MultipartFile filePath,
                                      @RequestParam(required = false) boolean resetPassword) throws IOException, InterruptedException, IOException {
        if (user.getUserId() == null) {// 用户对象的用户id是null，表示新增
            // 先判断用户名（学号）是否被占用，合成条件：未冻结，且role_id是4(学生)，且用户名是新增用户名
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("is_lock", 0).eq("role_id", 4).eq("user_name", user.getUserName());
            if (userService.count(wrapper) > 0) {// 统计符合条件的记录数，如果大于0，表示用户名被占了
                return ResponseResult.error("用户名被占用，请换一个");// 用户名被占用，返回错误信息
            }
            // 执行到此处表示用户名没有被占用，设置新学生的一些固定信息
            user.setRoleId(4L);// 设置role_id是4
            user.setIsLock(0);// 设置未冻结
        }
        String imgpath = userService.upload(filePath);// 上传头像
        if (!StringUtil.isEmpty(imgpath)) {// 如果头像上传成功，上传后的文件名不能为空
            user.setImgPath(imgpath);// 将上传后的文件名设置到学生对象的umg_path字段
        } else if (user.getUserId() == null) {// 如果上传后的文件名是null，且是新增用户
            user.setImgPath("oasys.jpg");// 为学生设置一个固定的头像
        }// 注意：如果是修改信息，且没有重新上传头像，维持user对象里的imgPath不变，即不跟该本来的头像
        if (resetPassword) {
            user.setPassword("123456");// 如果是用户选择重置密码，或重置密码（默认密码123456）
        }
        user.setModifyTime(LocalDateTime.now());// 设置修改时间
        return userService.saveOrUpdate(user) ? ResponseResult.ok("保存成功") : ResponseResult.ok("保存失败");
    }

    /**
     * 按照id删除学生，非真实删除记录，将is_lock字段设置为1
     *
     * @param userId, 用户id(学号)
     * @return: 删除成功: ok(成功)，失败: error(失败)
     */
    @DeleteMapping("/delete")
    public ResponseResult deleteStudent(Long userId) {
        User user = new User();
        user.setUserId(userId);
        user.setIsLock(1);
        return userService.updateById(user) ? ResponseResult.ok("删除成功！") : ResponseResult.error("删除失败！");
    }
}