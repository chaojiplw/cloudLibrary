package com.itheima.controller;

import com.itheima.entity.PageResult;
import com.itheima.service.RecordService;
import com.itheima.domain.Record;
import com.itheima.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/record")
public class RecordController {
    
    @Autowired
    private RecordService recordService;
    
    /**
     * 查询借阅记录
     * @param record 查询条件
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @param model 模型
     * @param session HttpSession对象
     * @return 视图名称
     */
    @RequestMapping("/searchRecords")
    public String searchRecords(Record record, Integer pageNum, Integer pageSize, Model model, HttpSession session) {
        // 设置默认值
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10; // 每页默认显示10条数据
        }
        
        // 获取当前登录用户
        User user = (User) session.getAttribute("USER_SESSION");
        
        // 如果不是管理员，只能查询自己的借阅记录
        if (!"ADMIN".equals(user.getRole())) {
            record.setBorrower(user.getName());
        }
        
        // 调用服务层方法查询借阅记录
        PageResult pageResult = recordService.searchRecords(record, user, pageNum, pageSize);
        
        // 将结果添加到model中
        model.addAttribute("pageResult", pageResult);
        model.addAttribute("search", record);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("gourl", "/record/searchRecords");
        
        // 返回视图名称
        return "admin/record";
    }
}