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
            System.out.println("Finding book with id: " + id); // 添加调试信息
            Book book = bookService.findById(id);
            System.out.println("Book found: " + book); // 添加调试信息
            if (book != null) {
                System.out.println("Found book: " + book.getId()); // 调试信息
                return new Result(true, "查询成功！", book);
            } else {
                System.out.println("No book found with id: " + id); // 添加调试信息
                return new Result(false, "未找到指定图书！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "查询失败！");
        }
    }
    
    /**
     * 查询最新上架的图书（仅显示前5本）
     * @param model 模型
     * @return 视图名称
     */
    @RequestMapping("/selectNewBooks")
    public String selectNewBooks(Model model) {
        // 查询最新上架的图书，固定显示前5本
        PageResult pageResult = bookService.selectNewBooks(1, 5);
        
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
     * 查询当前借阅的图书
     * @param book 查询条件
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @param model 模型
     * @param session HttpSession对象
     * @return 视图名称
     */
    @RequestMapping("/searchBorrowed")
    public String searchBorrowed(Book book, Integer pageNum, Integer pageSize, Model model, HttpSession session) {
        // 设置默认值
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10; // 每页默认显示10条数据
        }
        
        // 获取当前登录用户
        com.itheima.domain.User user = (com.itheima.domain.User) session.getAttribute("USER_SESSION");
        
        // 调用服务层方法查询当前借阅的图书信息
        PageResult pageResult = bookService.searchBorrowed(book, user, pageNum, pageSize);
        
        // 将结果添加到model中
        model.addAttribute("pageResult", pageResult);
        model.addAttribute("search", book);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("gourl", "/book/searchBorrowed");
        
        // 返回视图名称
        return "admin/book_borrowed";
    }
    
    /**
     * 新增图书
     * @param book 图书对象
     * @return 响应结果
     */
    @ResponseBody
    @RequestMapping("/addBook")
    public Result addBook(Book book) {
        try {
            // 设置新增图书的状态为"可借阅"(0)
            book.setStatus("0");
            Integer count = bookService.addBook(book);
            if (count != 1) {
                return new Result(false, "新增图书失败!");
            }
            return new Result(true, "新增图书成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "新增图书失败!");
        }
    }
    
    /**
     * 编辑图书
     * @param book 图书对象
     * @return 响应结果
     */
    @ResponseBody
    @RequestMapping("/editBook")
    public Result editBook(Book book) {
        try {
            // 保留原图书的借阅相关信息
            Book originalBook = bookService.findById(book.getId());
            if (originalBook != null) {
                book.setBorrower(originalBook.getBorrower());
                book.setBorrowTime(originalBook.getBorrowTime());
                book.setReturnTime(originalBook.getReturnTime());
            }
            
            Integer count= bookService.editBook(book);
            if(count!=1) { return new Result(false, "编辑失败!"); }
            return new Result(true, "编辑成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "编辑失败!");
        }
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