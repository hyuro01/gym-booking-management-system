package com.gymbook.manage.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gymbook.manage.DTO.UserCourseDTO;
import com.gymbook.manage.domain.Book;
import com.gymbook.manage.domain.Course;

/**
 * 预约信息Service接口
 * 
 * @author wooyoungnauh
 * @date 2025-02-22
 */
public interface IBookService 
{
    /**
     * 查询预约信息
     * 
     * @param id 预约信息主键
     * @return 预约信息
     */
    public Book selectBookById(Long id);

    /**
     * 查询预约信息列表
     * 
     * @param book 预约信息
     * @return 预约信息集合
     */
    public List<Book> selectBookList(Book book);

    /**
     * 新增预约信息
     * 
     * @param book 预约信息
     * @return 结果
     */
    public int insertBook(Book book);

    /**
     * 修改预约信息
     * 
     * @param book 预约信息
     * @return 结果
     */
    public int updateBook(Book book);

    /**
     * 批量删除预约信息
     * 
     * @param ids 需要删除的预约信息主键集合
     * @return 结果
     */
    public int deleteBookByIds(Long[] ids);

    /**
     * 删除预约信息信息
     * 
     * @param id 预约信息主键
     * @return 结果
     */
    public int deleteBookById(Long id);

    /**
     * 获取所有用户的课程预约记录
     * @return
     */
    public List<UserCourseDTO> getAllUserBooking();


    List<Book> getEndBook(int userId);
}
