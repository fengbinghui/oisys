package cn.njcit.service;

import cn.njcit.dto.LoginResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface IAuthService {
    LoginResult login(String userName, String password, HttpSession session, HttpServletRequest request);
}
