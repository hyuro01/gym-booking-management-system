package com.gymbook.manage.mapper;

import com.gymbook.manage.domain.Book;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户申请Mapper接口
 * 
 * @author wooyoungnauh
 * @date 2025-02-23
 */
@Mapper
public interface UserApplyMapper
{
    /**
     * 查询预约信息
     * 
     * @param id 预约信息主键
     * @return 预约信息
     */
    public Book selectBookById(Long id);

    /**
     * 查询用户提交的申请
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
     * 删除预约信息
     * 
     * @param id 预约信息主键
     * @return 结果
     */
    public int deleteBookById(Long id);

    /**
     * 批量删除预约信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBookByIds(Long[] ids);
}
