package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.domain.Book;
import com.itheima.domain.User;

// 图书接口
public interface BookService {
    /**
     * 根据ID查询图书
     * @param id 图书ID
     * @return 图书对象
     */
    Book findById(Integer id);
    
    /**
     * 查询最新上架的图书
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return 分页结果
     */
    PageResult selectNewBooks(Integer pageNum, Integer pageSize);
    
    /**
     * 借阅图书
     * @param book 图书对象
     * @return 是否借阅成功
     */
    boolean borrowBook(Book book);
    
    /**
     * 分页查询图书
     * @param book 查询条件
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return 分页结果
     */
    PageResult search(Book book, Integer pageNum, Integer pageSize);
    
    /**
     * 查询当前借阅的图书
     * @param book 查询条件
     * @param user 当前用户
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return 分页结果
     */
    PageResult searchBorrowed(Book book, User user, Integer pageNum, Integer pageSize);
    
    /**
     * 新增图书
     * @param book 图书对象
     * @return 影响的行数
     */
    Integer addBook(Book book);
    
    /**
     * 编辑图书信息
     * @param book 图书对象
     * @return 影响的行数
     */
    Integer editBook(Book book);
    
    /**
     * 归还图书
     * @param id 图书ID
     * @param user 当前用户
     * @return 是否归还成功
     */
    boolean returnBook(String id, User user);
    
    /**
     * 归还确认
     * @param id 图书ID
     * @return 影响的行数
     */
    Integer returnConfirm(String id);
}