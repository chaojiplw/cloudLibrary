package com.itheima.interceptor;

import com.itheima.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.logging.Logger;
import java.util.logging.Level;

public class ResourcesInterceptor implements HandlerInterceptor {
    
    private static final Logger logger = Logger.getLogger(ResourcesInterceptor.class.getName());
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求的路径
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        
        // 记录请求信息
        logger.log(Level.INFO, "拦截器处理请求: " + uri);
        
        // 如果是访问登录相关的路径或静态资源，则直接放行
        if (uri.equals(contextPath + "/") ||
            uri.equals(contextPath + "/admin/login") ||
            uri.startsWith(contextPath + "/user/login") ||
            uri.startsWith(contextPath + "/css/") ||
            uri.startsWith(contextPath + "/js/") ||
            uri.startsWith(contextPath + "/img/") ||
            uri.startsWith(contextPath + "/fonts/")) {
            logger.log(Level.INFO, "放行请求: " + uri);
            return true;
        }
        
        // 从session中获取用户信息
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("USER_SESSION");
        
        // 记录用户登录状态
        if (user != null) {
            logger.log(Level.INFO, "用户已登录，用户名: " + user.getName());
        } else {
            logger.log(Level.INFO, "用户未登录，session为空");
        }
        
        // 如果用户已登录，放行
        if (user != null) {
            return true;
        }
        
        // 未登录，重定向到登录页面，并携带未登录提示参数
        logger.log(Level.INFO, "重定向到登录页面");
        response.sendRedirect(request.getContextPath() + "/admin/login?message=" + java.net.URLEncoder.encode("请先登录系统", "UTF-8"));
        return false;
    }
}