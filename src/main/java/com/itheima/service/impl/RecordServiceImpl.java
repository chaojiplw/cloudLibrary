package com.itheima.service.impl;

import com.github.pagehelper.PageHelper;
import com.itheima.dao.RecordMapper;
import com.itheima.domain.Record;
import com.itheima.domain.User;
import com.itheima.entity.PageResult;
import com.itheima.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordServiceImpl implements RecordService {
    
    @Autowired
    private RecordMapper recordMapper;
    
    /**
     * 新增借阅记录
     * @param record 新增的借阅记录
     * @return 影响的行数
     */
    @Override
    public Integer addRecord(Record record) {
        return recordMapper.addRecord(record);
    }
    
    /**
     * 分页查询借阅记录
     */
    @Override
    public PageResult searchRecords(Record record, User user, Integer pageNum, Integer pageSize) {
        // 设置分页查询的参数，开始分页
        PageHelper.startPage(pageNum, pageSize);
        //如果不是管理员，则查询条件中的借阅人设置为当前登录用户
        if(!"ADMIN".equals(user.getRole())){
            record.setBorrower(user.getName());
        }
        // 执行分页查询
        com.github.pagehelper.Page<Record> page = recordMapper.searchRecords(record);
        // 封装分页结果
        return new PageResult(page.getTotal(), page.getResult());
    }
}