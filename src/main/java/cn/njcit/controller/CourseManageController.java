package cn.njcit.controller;

import cn.njcit.entity.AttendClassSetting;
import cn.njcit.entity.Course;
import cn.njcit.entity.CourseManage;
import cn.njcit.entity.OIClass;
import cn.njcit.dto.CourseManageSaveRequest;
import cn.njcit.exception.BizException;
import cn.njcit.service.IClassService;
import cn.njcit.service.ICourseService;
import cn.njcit.service.ICourseManageService;
import cn.njcit.util.ResponseResult;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程管理表 前端控制器
 * </p>
 *
 * @author selena
 * @since 2023-10-24
 */
@RestController
@RequestMapping("/courseManage")
public class CourseManageController {
    @Autowired
    private ICourseManageService courseManageService;
    @Autowired
    private IClassService classService;
    @Autowired
    private ICourseService courseService;

    @GetMapping("/list")
    public ResponseResult courseManageManage(@RequestParam(defaultValue = "1") Integer page,
                                             @RequestParam(required = false) String searchName) {
        PageInfo<CourseManage> courseManageList = courseManageService.getCourseManageList(page, searchName);
        return ResponseResult.ok().put("courseManageList", courseManageList);
    }

    @GetMapping("/edit")
    public ResponseResult courseManageEdit(@RequestParam(required = false) Long id) {
        ResponseResult responseResult = ResponseResult.ok();
        List<Course> courseList = courseService.list();
        List<OIClass> classList = classService.list();
        responseResult.put("courseList", courseList);
        responseResult.put("classList", classList);
        if (id != null) {   // id不是空，表示要修改课程安排，返回该课程安排对象
            List<CourseManage> courseManage = courseManageService.getCourseManageById(id);
            if (!courseManage.isEmpty()) {
                responseResult.put("courseManage", courseManage.get(0));
                responseResult.put("attendClassSettingList", courseManage.get(0).getAttendClassSettings());
            }
        } else {
            responseResult.put("attendClassSettingList", new ArrayList<>());
        }
        return responseResult;
    }

    @PostMapping("/save")
    public ResponseResult saveCourseManage(@RequestBody @Valid CourseManageSaveRequest requestBody) {
        CourseManageSaveRequest.CourseManagePayload courseManagePayload = requestBody.getCourseManage();
        CourseManage courseManage = new CourseManage();
        courseManage.setId(courseManagePayload.getId());
        courseManage.setCourseNo(courseManagePayload.getCourseNo());
        courseManage.setCourseName(courseManagePayload.getCourseName());
        courseManage.setTeacherName(courseManagePayload.getTeacherName());
        courseManage.setTeacherNo(emptyToBlank(courseManagePayload.getTeacherNo()));
        courseManage.setClassNo(emptyToBlank(courseManagePayload.getClassNo()));
        courseManage.setClassName(courseManagePayload.getClassName());

        List<AttendClassSetting> settings = new ArrayList<>();
        if (requestBody.getAttendClassSettingList() != null) {
            for (CourseManageSaveRequest.AttendClassSettingPayload item : requestBody.getAttendClassSettingList()) {
                AttendClassSetting setting = new AttendClassSetting();
                setting.setAttendClassWeek(item.getAttendClassWeek());
                setting.setStartTime(item.getStartTime());
                setting.setEndTime(item.getEndTime());
                setting.setAddress(item.getAddress());
                settings.add(setting);
            }
        }

        boolean saved = courseManageService.saveCourseManageWithSettings(courseManage, settings);
        if (!saved) {
            throw new BizException("保存失败");
        }
        return ResponseResult.ok("保存成功");
    }

    @DeleteMapping("/delete")
    public ResponseResult deleteCourse(Long id) {
        return courseManageService.deleteCourseManage(id) ? ResponseResult.ok("成功删除！") : ResponseResult.error("删除失败");
    }

    private String emptyToBlank(String value) {
        return value == null ? "" : value;
    }
}
