package cn.njcit.controller;

import cn.njcit.entity.Course;
import cn.njcit.service.ICourseService;
import cn.njcit.util.ResponseResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.io.IOException;

/**
 * <p>
 * 课程信息 前端控制器
 * </p>
 *
 * @author selena
 * @since 2023-10-24
 */
@RestController
@RequestMapping("/courseMan")
public class CourseController {
    @Autowired
    private ICourseService courseService;

    /**
     * 指定页号,查询所有课程信息
     *
     * @param page:页号
     * @return: 响应消息携带课程
     */
    @GetMapping("/list")
    public ResponseResult courseMan(@RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(required = false) String searchName) {
        PageInfo<Course> courseList = courseService.getAllCourseList(page, searchName);
        return ResponseResult.ok().put("courseList", courseList);
    }

    /**
     * 新增或者修改课程信息第一步。如果是新增课程，返回课程列表；如果是修改课程信息，返回按照id查询到的课程信息
     *
     * @param id：班级的编号，可能为null，如果为null则是新增班级
     * @return: ResponseResult(, 班级对象)
     */
    @GetMapping("/edit")
    public ResponseResult courseEdit(@RequestParam(required = false) Long id) {
        ResponseResult responseResult = ResponseResult.ok();
        if (id != null) {// id不是空，表示要修改课程信息，返回课程对象
            Course course = courseService.getCourseById(id);
            responseResult.put("course", course);// 课程对象加入responseResult
        }
        return responseResult;
    }

    /**
     * insert/update课程信息，如果curse对象中的课程编号不是空，表示更新，如果课程编号是空，表示新增课程
     *
     * @param course :课程对象
     * @return : 课程编号重复(新增时),error(被占用)，正常新增或更新:ok("保存成功"),新增或更新出错:error(保存失败)
     * @throws :InterruptedException
     * @throws: IOException
     */
    @PostMapping("/save")
    public ResponseResult saveCourse(Course course) throws IOException, InterruptedException {
        if (course.getId() == null) {// 课程对象的课程id是null，表示新增
            // 判断课程编号是否被占用
            QueryWrapper<Course> wrapper = new QueryWrapper<>();
            wrapper.eq("course_no", course.getCourseNo());
            if (courseService.count(wrapper) > 0) {// 统计符合条件的
                return ResponseResult.error("课程编号被占用，请换一个！");// 班级名被占用，返回错误信息
            }
        }
        return courseService.saveOrUpdate(course) ? ResponseResult.ok("保存成功！") : ResponseResult.error("保存失败！");
    }

    /**
     * 按照id删除课程
     *
     * @param id:课程id
     * @return : 删除成功，失败
     */
    @DeleteMapping("/delete")
    public ResponseResult deleteCourse(Long id) {
        return courseService.deleteCourse(id) ? ResponseResult.ok("成功删除！") : ResponseResult.error("删除失败");
    }
}
