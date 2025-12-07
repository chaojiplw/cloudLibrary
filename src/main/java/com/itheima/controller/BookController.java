package com.itheima.controller;

import com.itheima.entity.PageResult;
import com.itheima.service.BookService;
import com.itheima.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/book")
public class BookController {
    
    @Autowired
    private BookService bookService;
    
    /**
     * 根据ID查询图书信息
     * @param id 图书ID
     * @return 图书信息
     */
    @RequestMapping("/findById")
    @ResponseBody
    public Result findById(Integer id) {
        try {
            Book book = bookService.findById(id);
            if (book != null) {
                return new Result(true, "查询成功！", book);
            } else {
                return new Result(false, "未找到指定图书！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "查询失败！");
        }
    }
    
    /**
     * 查询最新上架的图书
     * @param pageNum 页码，默认为1
     * @param pageSize 每页数量，默认为5
     * @return 分页结果
     */
    @RequestMapping("/selectNewBooks")
    public String selectNewBooks(Integer pageNum, Integer pageSize, Model model) {
        // 设置默认值
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 5;
        }
        
        // 调用服务层方法查询最新上架的图书
        PageResult pageResult = bookService.selectNewBooks(pageNum, pageSize);
        
        // 将结果添加到model中
        model.addAttribute("pageResult", pageResult);
        
        // 返回视图名称
        return "admin/books_new";
    }
    
    /**
     * 借阅图书
     * @param book 图书对象
     * @param session HttpSession对象
     * @return 响应结果
     */
    @RequestMapping("/borrowBook")
    @ResponseBody
    public Result borrowBook(Book book, HttpSession session) {
        try {
            // 设置借阅人（从session中获取当前登录用户）
            com.itheima.domain.User user = (com.itheima.domain.User) session.getAttribute("USER_SESSION");
            if (user == null) {
                return new Result(false, "请先登录！");
            }
            
            // 设置图书状态为"借阅中"
            book.setStatus("1"); // 1表示借阅中
            
            // 设置借阅人
            book.setBorrower(user.getName());
            
            // 设置借阅时间为当前时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            book.setBorrowTime(sdf.format(new Date()));
            
            // 调用服务层方法借阅图书
            boolean flag = bookService.borrowBook(book);
            
            if (flag) {
                return new Result(true, "借阅成功！");
            } else {
                return new Result(false, "借阅失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "借阅失败！");
        }
    }
    
    /**
     * 分页查询图书信息
     * @param book 查询条件
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @param model 模型
     * @return 视图名称
     */
    @RequestMapping("/search")
    public String search(Book book, Integer pageNum, Integer pageSize, Model model) {
        // 设置默认值
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10; // 每页默认显示10条数据
        }
        
        // 调用服务层方法查询图书信息
        PageResult pageResult = bookService.search(book, pageNum, pageSize);
        
        // 将结果添加到model中
        model.addAttribute("pageResult", pageResult);
        model.addAttribute("search", book);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("gourl", "/book/search");
        
        // 返回视图名称
        return "admin/books";
    }
    
    /**
     * 响应结果内部类
     */
    public static class Result {
        private boolean success;
        private String message;
        private Book data; // 添加data字段用于返回图书信息
        
        public Result() {}
        
        public Result(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
        
        public Result(boolean success, String message, Book data) {
            this.success = success;
            this.message = message;
            this.data = data;
        }
        
        public boolean isSuccess() {
            return success;
        }
        
        public void setSuccess(boolean success) {
            this.success = success;
        }
        
        public String getMessage() {
            return message;
        }
        
        public void setMessage(String message) {
            this.message = message;
        }
        
        public Book getData() {
            return data;
        }
        
        public void setData(Book data) {
            this.data = data;
        }
    }
}