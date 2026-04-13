package cn.njcit.controller;


import cn.njcit.dto.LoginResult;
import cn.njcit.entity.User;
import cn.njcit.exception.BizException;
import cn.njcit.service.IAuthService;
import cn.njcit.service.ISysMenuService;
import cn.njcit.service.IUserService;
import cn.njcit.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/")
public class LoginController {
    @Autowired
    private IUserService userService;// 注入IUserService
    @Autowired
    private IAuthService authService;
    @Autowired
    private ISysMenuService sysMenuService;

    // 登录，根据用户名和密码进行查找
    @PostMapping(value = "login")
    public ResponseResult loginCheck(String userName, String password, HttpSession session, HttpServletRequest request) {
        LoginResult result = authService.login(userName, password, session, request);
        return ResponseResult.ok()
                .put("userId", result.getUserId())
                .put("realName", result.getRealName())
                .put("className", result.getClassName())
                .put("roleId", result.getRoleId())
                .put("superman", result.getSuperman())
                .put("imgPath", result.getImgPath())
                .put("menus", result.getMenus());
    }

    @GetMapping("/currentUser")
    public ResponseResult currentUser(HttpSession session) {
        Object userId = session.getAttribute("userId");
        if (userId == null) {
            throw new BizException(401, "未登录");
        }
        User user = userService.getById((Long) userId);
        if (user == null) {
            throw new BizException("用户不存在");
        }
        return ResponseResult.ok()
                .put("userId", user.getUserId())
                .put("roleId", user.getRoleId())
                .put("superman", user.getSuperman())
                .put("menus", sysMenuService.getMenusByRoleId(user.getRoleId()));
    }

    @GetMapping("/logout")
    public ResponseResult logout(HttpSession session) {
        session.removeAttribute("userId");
        return ResponseResult.ok("成功退出登录！");
    }
}
