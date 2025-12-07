package com.itheima.controller;

import com.itheima.domain.User;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @RequestMapping("/login")
    public String login(User user, HttpSession session) {
        User loginUser = userService.login(user);
        if (loginUser != null) {
            // 登录成功，将用户信息存入session
            session.setAttribute("USER_SESSION", loginUser);
            // 返回到主页
            return "redirect:/admin/main.jsp";
        } else {
            // 登录失败，返回登录页并提示错误
            try {
                return "redirect:/admin/login?message=" + URLEncoder.encode("你还没有登录/用户名密码错误", "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return "redirect:/admin/login";
            }
        }
    }
    
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) throws UnsupportedEncodingException {
        try { 
            HttpSession session = request.getSession();
            session.invalidate();//销毁Session
            return  "redirect:/admin/login";
        } catch(Exception e) { 
            e.printStackTrace();
            return  "redirect:/admin/login?message=" + URLEncoder.encode("系统错误", "UTF-8");
        }
    }
}