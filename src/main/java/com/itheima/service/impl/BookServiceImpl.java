package com.itheima.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.BookMapper;
import com.itheima.entity.PageResult;
import com.itheima.service.BookService;
import com.itheima.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    
    @Autowired
    private BookMapper bookMapper;
    
    @Override
    public Book findById(Integer id) {
        return bookMapper.findById(id);
    }
    
    @Override
    public PageResult selectNewBooks(Integer pageNum, Integer pageSize) {
        // 使用PageHelper进行分页
        PageHelper.startPage(pageNum, pageSize);
        
        // 查询最新上架的图书
        List<com.itheima.domain.Book> list = bookMapper.selectNewBooks();
        
        // 使用PageInfo包装结果
        PageInfo<com.itheima.domain.Book> pageInfo = new PageInfo<>(list);
        
        // 封装分页结果
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
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
}