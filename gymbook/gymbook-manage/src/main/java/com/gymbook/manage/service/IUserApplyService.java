package com.gymbook.manage.service;

import com.gymbook.manage.domain.Book;

import java.util.List;

/**
 * 用户申请Service接口
 * 
 * @author wooyoungnauh
 * @date 2025-02-23
 */
public interface IUserApplyService
{
    /**
     * 查询预约信息
     * 
     * @param id 预约信息主键
     * @return 预约信息
     */
    public Book selectBookById(Long id);

    /**
     * 查询待处理的用户申请信息列表
     * 
     * @param book 预约信息
     * @return 预约信息集合
     */
    public List<Book> selectUserApplyList(Book book);


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
}
