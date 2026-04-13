package cn.njcit.controller;

import cn.njcit.entity.AttendsList;
import cn.njcit.service.IAttendsListService;
import cn.njcit.util.ResponseResult;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 考勤打卡 前端控制器
 * </p>
 *
 * @author selena
 * @since 2023-10-24
 */
@RestController
@RequestMapping("/attendance")
public class AttendsListController {

    @Autowired
    private IAttendsListService attendsListService;

    @GetMapping("/list")
    public ResponseResult classManage(@RequestParam(defaultValue = "1") Integer page, @RequestParam("searchDate") String searchDate) {
        PageInfo<AttendsList> classList = attendsListService.getAttendsList(page, searchDate);

        // 创建一个新的List，只包含需要的属性
        List<Map<String, Object>> resultList = new ArrayList<>();

        for (AttendsList attendsList : classList.getList()) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("user_name", attendsList.getUserName());
            resultMap.put("real_name", attendsList.getRealName());
            resultMap.put("class_name", attendsList.getClassName());
            resultMap.put("normal", attendsList.getNormal());
            resultMap.put("late", attendsList.getLate());
            resultMap.put("leave", attendsList.getLeave());
            resultMap.put("early_leave", attendsList.getEarlyLeave());
            resultMap.put("absent", attendsList.getAbsent());
            resultMap.put("makeup", attendsList.getMakeup());
            resultMap.put("missingCard", attendsList.getMissingCard());
            // 同样添加其他需要的属性

            resultList.add(resultMap);
        }

        PageInfo<Map<String, Object>> filteredList = new PageInfo<>();
        filteredList.setList(resultList);
        filteredList.setPageNum(classList.getPageNum());
        filteredList.setPageSize(classList.getPageSize());
        filteredList.setPages(classList.getPages());
        filteredList.setTotal(classList.getTotal());
        filteredList.setIsFirstPage(classList.isIsFirstPage());
        filteredList.setIsLastPage(classList.isIsLastPage());
        filteredList.setHasNextPage(classList.isHasNextPage());
        filteredList.setHasPreviousPage(classList.isHasPreviousPage());

        return ResponseResult.ok().put("classList", filteredList);
    }

    @GetMapping("/details")
    public ResponseResult attendanceDetails(@RequestParam(defaultValue = "1") Integer page,
                                            @RequestParam("userName") String userName,
                                            @RequestParam("searchDate") String searchDate) {
        PageInfo<AttendsList> detailPage = attendsListService.getAttendanceDetails(page, userName, searchDate);
        List<Map<String, Object>> detailList = new ArrayList<>();
        for (AttendsList detail : detailPage.getList()) {
            Map<String, Object> row = new HashMap<>();
            row.put("courseNo", detail.getCourseNo());
            row.put("courseName", detail.getCourseName());
            row.put("teacherName", detail.getTeacherName());
            row.put("className", detail.getClassName());
            row.put("typeName", detail.getTypeName());
            row.put("attendsTime", detail.getAttendsTime());
            row.put("clockTypeName", detail.getClockTypeName());
            row.put("clockAddress", detail.getClockAdress());
            row.put("statusName", detail.getStatusName());
            detailList.add(row);
        }
        PageInfo<Map<String, Object>> resultPage = new PageInfo<>();
        resultPage.setList(detailList);
        resultPage.setPageNum(detailPage.getPageNum());
        resultPage.setPageSize(detailPage.getPageSize());
        resultPage.setPages(detailPage.getPages());
        resultPage.setTotal(detailPage.getTotal());
        resultPage.setIsFirstPage(detailPage.isIsFirstPage());
        resultPage.setIsLastPage(detailPage.isIsLastPage());
        resultPage.setHasNextPage(detailPage.isHasNextPage());
        resultPage.setHasPreviousPage(detailPage.isHasPreviousPage());
        return ResponseResult.ok().put("detailList", resultPage);
    }


}
