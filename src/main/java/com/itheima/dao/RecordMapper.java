package com.itheima.dao;

import com.itheima.domain.Record;
import com.github.pagehelper.Page;

public interface RecordMapper {
    /**
     * 新增借阅记录
     * @param record 借阅记录对象
     * @return 影响的行数
     */
    Integer addRecord(Record record);
    
    /**
     * 分页查询借阅记录
     * @param record 查询条件
     * @return 借阅记录分页结果
     */
    Page<Record> searchRecords(Record record);
}