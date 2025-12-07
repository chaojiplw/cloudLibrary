package com.itheima.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Controller
public class LoginController {
    
    @RequestMapping("/admin/login")
    public String loginPage(@RequestParam(required = false) String message, HttpServletRequest request) throws UnsupportedEncodingException {
        // 如果有message参数，将其添加到request中
        if (message != null) {
            // URL解码
            String decodedMessage = URLDecoder.decode(message, "UTF-8");
            request.setAttribute("message", decodedMessage);
        }
        return "admin/login";
    }
}