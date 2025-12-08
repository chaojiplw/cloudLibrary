package com.itheima.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.BookMapper;
import com.itheima.entity.PageResult;
import com.itheima.service.BookService;
import com.itheima.domain.Book;
import com.itheima.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    
    @Autowired
    private BookMapper bookMapper;
    
    @Override
    public Book findById(Integer id) {
        System.out.println("Querying book with id: " + id); // 添加调试信息
        Book book = bookMapper.findById(id);
        System.out.println("Book returned from mapper: " + book); // 添加调试信息
        if (book != null) {
            System.out.println("Book id: " + book.getId()); // 添加调试信息
        }
        return book;
    }
    
    @Override
    public PageResult selectNewBooks(Integer pageNum, Integer pageSize) {
        // 查询最新上架的图书
        List<com.itheima.domain.Book> list = bookMapper.selectNewBooks();
        
        // 封装分页结果 (新书推荐功能固定显示5本，不需要真正的分页)
        return new PageResult(list.size(), list);
    }
    
    @Override
    public boolean borrowBook(Book book) {
        // 调用Mapper更新图书信息
        int result = bookMapper.updateBook(book);
        return result > 0;
    }
    
    @Override
    public PageResult search(Book book, Integer pageNum, Integer pageSize) {
        // 设置分页查询的参数，开始分页
        PageHelper.startPage(pageNum, pageSize);
        // 执行分页查询
        com.github.pagehelper.Page<Book> page = bookMapper.searchBooks(book);
        // 封装分页结果
        return new PageResult(page.getTotal(), page.getResult());
    }
    
    @Override
    public PageResult searchBorrowed(Book book, User user, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize); // 设置分页查询的参数，开始分页
        com.github.pagehelper.Page<Book> page;
        //如果是管理员，查询当前用户借阅但未归还的图书和所有待确认归还的图书
        if("ADMIN".equals(user.getRole())) {
            page= bookMapper.selectBorrowed(book);
        } else {//如果是普通用户，查询当前用户借阅但未归还的图书
            book.setBorrower(user.getName()); //将当前登录的用户放入查询条件中
            page= bookMapper.selectMyBorrowed(book);
        }
        return new PageResult(page.getTotal(),page.getResult());
    }
    
    @Override
    public Integer addBook(Book book) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //设置新增图书的上架时间
        book.setUploadTime(dateFormat.format(new Date()));
        return bookMapper.addBook(book);
    }
    
    /**
     * 编辑图书信息
     */
    @Override
    public Integer editBook(Book book) {
        return bookMapper.editBook(book);
    }
}