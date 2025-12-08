package com.itheima.service;

import com.itheima.domain.Record;
import com.itheima.domain.User;
import com.itheima.entity.PageResult;

/**
 * 借阅记录接口
 */
public interface RecordService {
    /**
     * 新增借阅记录
     * @param record 借阅记录对象
     * @return 影响的行数
     */
    Integer addRecord(Record record);
    
    /**
     * 分页查询借阅记录
     * @param record 查询条件
     * @param user 当前用户
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return 分页结果
     */
    PageResult searchRecords(Record record, User user, Integer pageNum, Integer pageSize);
}