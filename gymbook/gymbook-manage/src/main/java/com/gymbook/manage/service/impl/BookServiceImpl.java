package com.gymbook.manage.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gymbook.common.utils.DateUtils;
import com.gymbook.manage.DTO.UserCourseDTO;
import com.gymbook.manage.domain.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gymbook.manage.mapper.BookMapper;
import com.gymbook.manage.domain.Book;
import com.gymbook.manage.service.IBookService;

/**
 * 预约信息Service业务层处理
 * 
 * @author wooyoungnauh
 * @date 2025-02-22
 */
@Service
public class BookServiceImpl implements IBookService 
{
    @Autowired
    private BookMapper bookMapper;

    /**
     * 查询预约信息
     * 
     * @param id 预约信息主键
     * @return 预约信息
     */
    @Override
    public Book selectBookById(Long id)
    {
        return bookMapper.selectBookById(id);
    }

    /**
     * 查询预约信息列表
     * 
     * @param book 预约信息
     * @return 预约信息
     */
    @Override
    public List<Book> selectBookList(Book book)
    {
        return bookMapper.selectBookList(book);
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
        return bookMapper.insertBook(book);
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
        return bookMapper.updateBook(book);
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
        return bookMapper.deleteBookByIds(ids);
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
        return bookMapper.deleteBookById(id);
    }

    /**
     * 获取所有用户的课程预约记录
     * @return
     */
    @Override
    public List<UserCourseDTO> getAllUserBooking() {
        return bookMapper.getAllUserBooking();
    }

    /**
     * 获取已完成未评价的预约
     * @param userId
     * @return
     */
    @Override
    public List<Book> getEndBook(int userId) {
        return bookMapper.getEndBook(userId);
    }


}
