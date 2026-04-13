package cn.njcit.mapper;

import cn.njcit.entity.AttendsList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 考勤打卡 Mapper 接口
 * </p>
 *
 * @author selena
 * @since 2023-10-24
 */
public interface AttendsListMapper extends BaseMapper<AttendsList> {
    public List<AttendsList> getAllAttends();


    List<AttendsList> getAllAttendsByName(String searchName);


    @Select("SELECT " +
            "u.user_name, " +
            "u.real_name, " +
            "c.class_name, " +
            "SUM(CASE WHEN al.status_id = 10 THEN 1 ELSE 0 END) AS normal, " +
            "SUM(CASE WHEN al.status_id = 11 THEN 1 ELSE 0 END) AS late, " +
            "SUM(CASE WHEN al.status_id = 12 THEN 1 ELSE 0 END) AS early_leave, " +
            "SUM(CASE WHEN al.status_id = 13 THEN 1 ELSE 0 END) AS absent, " +
            "SUM(CASE WHEN al.status_id = 46 THEN 1 ELSE 0 END) AS level, " +
            "SUM(CASE WHEN al.status_id = 47 THEN 1 ELSE 0 END) AS makeup, " +
            "SUM(CASE WHEN al.status_id = 48 THEN 1 ELSE 0 END) AS missing_card " +
            "FROM " +
            "aoa_user AS u " +
            "LEFT JOIN " +
            "aoa_class AS c ON u.class_id = c.class_id " +
            "LEFT JOIN " +
            "aoa_attends_list AS al ON u.user_id = al.attends_user_id " +
            "WHERE " +
            "DATE_FORMAT(u.entrance_date, '%Y-%m') = #{searchDate} " +
            "GROUP BY " +
            "u.user_id, u.user_name, c.class_name")
    List<AttendsList> getAttendByDate(@Param("searchDate") String searchDate);

    @Select("SELECT " +
            "cm.course_no AS course_no, " +
            "cm.course_name AS course_name, " +
            "cm.teacher_name AS teacher_name, " +
            "c.class_name AS class_name, " +
            "CASE WHEN al.type_id = 8 THEN '上课打卡' WHEN al.type_id = 9 THEN '下课打卡' ELSE '未知' END AS type_name, " +
            "al.attends_time AS attends_time, " +
            "CASE WHEN al.clock_type = 1 THEN '手机端' ELSE 'PC端' END AS clock_type_name, " +
            "al.clock_adress AS clock_adress, " +
            "CASE " +
            "WHEN al.status_id = 10 THEN '正常' " +
            "WHEN al.status_id = 11 THEN '迟到' " +
            "WHEN al.status_id = 12 THEN '早退' " +
            "WHEN al.status_id = 13 THEN '缺勤' " +
            "WHEN al.status_id = 46 THEN '请假' " +
            "WHEN al.status_id = 47 THEN '补卡' " +
            "WHEN al.status_id = 48 THEN '缺卡' " +
            "ELSE '未知' END AS status_name " +
            "FROM aoa_attends_list al " +
            "LEFT JOIN aoa_user u ON u.user_id = al.attends_user_id " +
            "LEFT JOIN aoa_class c ON c.class_id = u.class_id " +
            "LEFT JOIN aoa_course_manage cm ON cm.id = al.course_id " +
            "WHERE u.user_name = #{userName} AND DATE_FORMAT(al.attends_time, '%Y-%m') = #{searchDate} " +
            "ORDER BY al.attends_time DESC")
    List<AttendsList> getAttendanceDetails(@Param("userName") String userName, @Param("searchDate") String searchDate);


}