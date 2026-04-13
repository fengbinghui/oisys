package cn.njcit.service.impl;

import cn.njcit.dto.LoginResult;
import cn.njcit.entity.OIClass;
import cn.njcit.entity.User;
import cn.njcit.entity.UserLoginRecord;
import cn.njcit.exception.BizException;
import cn.njcit.service.IAuthService;
import cn.njcit.service.IClassService;
import cn.njcit.service.ISysMenuService;
import cn.njcit.service.IUserLoginRecordService;
import cn.njcit.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements IAuthService {
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserLoginRecordService userLoginRecordService;
    @Autowired
    private IClassService classService;
    @Autowired
    private ISysMenuService sysMenuService;

    @Override
    public LoginResult login(String userName, String password, HttpSession session, HttpServletRequest request) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", userName).eq("password", password);
        User user = userService.getOne(wrapper);
        if (user == null) {
            throw new BizException("账号或密码错误！");
        }
        if (user.getIsLock() == 1) {
            throw new BizException("账号已被冻结！");
        }
        if (user.getStudentStatus() == 1) {
            throw new BizException("账号不在籍");
        }

        session.setAttribute("userId", user.getUserId());
        UserLoginRecord loginRecord = new UserLoginRecord();
        loginRecord.setBrowser(getBrowserInfo(request));
        loginRecord.setIpAddr(getClientIp(request));
        loginRecord.setLoginTime(LocalDateTime.now());
        loginRecord.setUserId(user.getUserId());
        userLoginRecordService.save(loginRecord);

        LoginResult result = new LoginResult();
        result.setUserId(user.getUserId());
        result.setRealName(user.getRealName());
        result.setClassName(getClassName(user.getClassId()));
        result.setRoleId(user.getRoleId());
        result.setSuperman(user.getSuperman());
        result.setImgPath(user.getImgPath());
        result.setMenus(sysMenuService.getMenusByRoleId(user.getRoleId()));
        return result;
    }

    private String getClassName(Long classId) {
        if (classId == null) {
            return "-";
        }
        OIClass oiClass = classService.getById(classId);
        if (oiClass == null || oiClass.getClassName() == null) {
            return "-";
        }
        return oiClass.getClassName();
    }

    private String getBrowserInfo(HttpServletRequest request) {
        Browser browser = UserAgent.parseUserAgentString(request.getHeader("User-Agent")).getBrowser();
        Version version = browser.getVersion(request.getHeader("User-Agent"));
        return browser.getName() + "/" + (version == null ? "unknown" : version.getVersion());
    }

    private String getClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.trim().isEmpty() && !"unknown".equalsIgnoreCase(xForwardedFor)) {
            return xForwardedFor.split(",")[0].trim();
        }
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.trim().isEmpty() && !"unknown".equalsIgnoreCase(xRealIp)) {
            return xRealIp.trim();
        }
        return request.getRemoteAddr();
    }
}
