package com.gymbook.manage.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gymbook.manage.DTO.BookStatusCountDTO;
import com.gymbook.manage.DTO.CoachCountDTO;
import com.gymbook.manage.DTO.CourseCountDTO;
import com.gymbook.manage.DTO.UserCourseDTO;
import com.gymbook.manage.domain.Book;
import com.gymbook.manage.domain.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 预约信息Mapper接口
 * 
 * @author wooyoungnauh
 * @date 2025-02-22
 */
@Mapper
public interface BookMapper 
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

    /**
     * 用户的预约次数
     * @param map
     * @return
     */
    int bookTimeByMap(Map map);

    /**
     * 统计指定时间区间内的教练预约量排名
     * @param begin
     * @param end
     * @return
     */
    List<CoachCountDTO> getCoachTop10(@Param("begin") LocalDateTime begin,@Param("end") LocalDateTime end);

    List<BookStatusCountDTO> getBookStatusCount(@Param("begin") LocalDateTime begin, @Param("end") LocalDateTime end);

    /**
     * 获取所有用户的课程预约记录
     * @return
     */
    @Select("        Select user_id, course_id, COUNT(*) as count\n" +
            "        FROM book\n" +
            "        WHERE status IN (1,3) AND course_id IS NOT NULL\n" +
            "        GROUP BY user_id, course_id")
    List<UserCourseDTO> getAllUserBooking();

    /**
     * 获取所有用户的课程预约记录
     * @return
     */
    //@Select("SELECT *  from  book  WHERE status IN (1,3) AND course_id IS NOT NULL")
    List<Book> getUserBooking();


    /**
     * 获取已完成未评价的预约
     * @param userId
     * @return
     */
    List<Book> getEndBook(int userId);

    /**
     * 教练被预约次数
     * @param map
     * @return
     */
    int coachStudentByMap(Map map);
}
