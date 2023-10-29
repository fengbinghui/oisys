package cn.njcit.controller;

import cn.njcit.entity.CourseManage;
import cn.njcit.service.IClassService;
import cn.njcit.service.ICourseManageService;
import cn.njcit.util.ResponseResult;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/list")
    public ResponseResult courseManageManage(@RequestParam(defaultValue = "1") Integer page,
                                             @RequestParam(required = false) String searchName) {
        PageInfo<CourseManage> courseManageList = courseManageService.getCourseManageList(page, searchName);
        return ResponseResult.ok().put("courseManageList", courseManageList);
    }

    @GetMapping("/edit")
    public ResponseResult coursemanageEdit(@RequestParam(required = false) Long id) {
        ResponseResult responseResult = ResponseResult.ok();
        if (id != null) {
            List<CourseManage> courseManageById = courseManageService.getCourseManageById(id);
            responseResult.put("course", courseManageById);
        }
        return responseResult;
    }
}
