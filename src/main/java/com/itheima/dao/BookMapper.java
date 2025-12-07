package com.itheima.dao;

import com.itheima.domain.Book;
import java.util.List;

public interface BookMapper {
    /**
     * 根据ID查询图书信息
     * @param id 图书ID
     * @return 图书对象
     */
    Book findById(Integer id);
    
    /**
     * 根据上架时间查询最新上架的图书信息
     * @return 图书列表
     */
    List<Book> selectNewBooks();
    
    /**
     * 更新图书信息（用于借阅图书）
     * @param book 图书对象
     * @return 影响的行数
     */
    int updateBook(Book book);
}