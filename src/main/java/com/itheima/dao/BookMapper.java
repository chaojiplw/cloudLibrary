package com.itheima.dao;

import com.itheima.domain.Book;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import com.github.pagehelper.Page;
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
    
    /**
     * 分页查询图书信息
     * @param book 查询条件
     * @return 图书分页结果
     */
    @Select({"<script>" +
            "SELECT * FROM book where book_status !='3'" +
            "<if test=\"name != null and name != ''\"> AND book_name like CONCAT('%',#{name},'%')</if>" +
            "<if test=\"press != null and press != ''\"> AND book_press like CONCAT('%', #{press},'%') </if>" +
            "<if test=\"author != null and author != ''\"> AND book_author like CONCAT('%', #{author},'%')</if>" +
            " order by book_status" +
            "</script>"})
    @ResultMap("bookMap")
    Page<Book> searchBooks(Book book);
    
    /**
     * 新增图书
     * @param book 图书对象
     * @return 影响的行数
     */
    int addBook(Book book);
}