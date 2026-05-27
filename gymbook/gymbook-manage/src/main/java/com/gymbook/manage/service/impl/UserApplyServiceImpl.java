package com.gymbook.manage.service.impl;

import com.gymbook.common.utils.DateUtils;
import com.gymbook.manage.domain.Book;
import com.gymbook.manage.mapper.BookMapper;
import com.gymbook.manage.mapper.UserApplyMapper;
import com.gymbook.manage.service.IBookService;
import com.gymbook.manage.service.IUserApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 预约信息Service业务层处理
 * 
 * @author wooyoungnauh
 * @date 2025-02-22
 */
@Service
public class UserApplyServiceImpl implements IUserApplyService
{
    @Autowired
    private UserApplyMapper userApplyMapper;

    /**
     * 查询预约信息
     * 
     * @param id 预约信息主键
     * @return 预约信息
     */
    @Override
    public Book selectBookById(Long id)
    {
        return userApplyMapper.selectBookById(id);
    }

    /**
     * 查询预约信息列表
     * 
     * @param book 预约信息
     * @return 预约信息
     */
    @Override
    public List<Book> selectUserApplyList(Book book)
    {
        return userApplyMapper.selectUserApplyList(book);
    }

    /**
     * 新增预约信息
     * 
     * @param book 预约信息
     * @return 结果
     */
    @Override
    public int insertBook(Book book)
    {
        book.setCreateTime(DateUtils.getNowDate());
        return userApplyMapper.insertBook(book);
    }

    /**
     * 修改预约信息
     * 
     * @param book 预约信息
     * @return 结果
     */
    @Override
    public int updateBook(Book book)
    {
        return userApplyMapper.updateBook(book);
    }

    /**
     * 批量删除预约信息
     * 
     * @param ids 需要删除的预约信息主键
     * @return 结果
     */
    @Override
    public int deleteBookByIds(Long[] ids)
    {
        return userApplyMapper.deleteBookByIds(ids);
    }

    /**
     * 删除预约信息信息
     * 
     * @param id 预约信息主键
     * @return 结果
     */
    @Override
    public int deleteBookById(Long id)
    {
        return userApplyMapper.deleteBookById(id);
    }
}
