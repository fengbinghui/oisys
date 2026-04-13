package cn.njcit.controller;

import cn.njcit.entity.Role;
import cn.njcit.entity.User;
import cn.njcit.service.IRoleService;
import cn.njcit.service.IUserService;
import cn.njcit.util.ResponseResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/userManage")
public class UserManageController {
    private final IUserService userService;
    private final IRoleService roleService;

    public UserManageController(IUserService userService, IRoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/list")
    public ResponseResult list(@RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(required = false) String searchName) {
        PageInfo<User> userList = userService.getUserList(page, searchName);
        return ResponseResult.ok().put("userList", userList);
    }

    @GetMapping("/edit")
    public ResponseResult edit(@RequestParam(required = false) Long userId) {
        ResponseResult result = ResponseResult.ok();
        if (userId != null) {
            result.put("user", userService.getById(userId));
        }
        result.put("roles", roleService.list());
        return result;
    }

    @PostMapping("/save")
    public ResponseResult save(User user, @RequestParam(required = false) boolean resetPassword) {
        if (user.getUserId() == null) {
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("is_lock", 0).eq("user_name", user.getUserName());
            if (userService.count(wrapper) > 0) {
                return ResponseResult.error("用户名被占用，请换一个");
            }
            user.setIsLock(0);
            if (user.getRoleId() == null) {
                user.setRoleId(4L);
            }
        }
        if (resetPassword || user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            user.setPassword("123456");
        }
        user.setModifyTime(LocalDateTime.now());
        return userService.saveOrUpdate(user) ? ResponseResult.ok("保存成功") : ResponseResult.error("保存失败");
    }

    @DeleteMapping("/remove")
    public ResponseResult remove(Long userId) {
        User user = new User();
        user.setUserId(userId);
        user.setIsLock(1);
        return userService.updateById(user) ? ResponseResult.ok("删除成功") : ResponseResult.error("删除失败");
    }
}
