package cn.njcit.controller;

import cn.njcit.entity.OIClass;
import cn.njcit.service.IClassService;
import cn.njcit.util.ResponseResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.io.IOException;

/**
 * <p>
 * 班级信息 前端控制器
 * </p>
 *
 * @author selena
 * @since 2023-10-24
 */
@RestController
@RequestMapping("/classManage")
public class ClassController {
    @Autowired
    private IClassService classService;

    // 查询所有班级信息
    @GetMapping("/list")
    public ResponseResult classManage(@RequestParam(defaultValue = "1") Integer page, @RequestParam(required = false) String searchName) {
        PageInfo<OIClass> classList = classService.getClassList(page, searchName);
        return ResponseResult.ok().put("classList", classList);
    }

    // 新增和修改班级信息
    @GetMapping("/edit")
    public ResponseResult classEdit(@RequestParam(required = false) Long classId) {
        ResponseResult responseResult = ResponseResult.ok();
        if (classId != null) {// classid不是空，表示要修改班级信息，返回该班级信息
            OIClass oiClass = classService.getClassById(classId);
            responseResult.put("oiClass", oiClass);// 班级对象加入responResult
        }
        return responseResult;
    }

    @PostMapping("/save")
    public ResponseResult saveClass(OIClass oiClass) throws IOException, InterruptedException {
        if (oiClass.getClassId() == null) {
            QueryWrapper<OIClass> wrapper = new QueryWrapper<>();
            wrapper.eq("class_no", oiClass.getClassNo());
            if (classService.count(wrapper) > 0) {
                return ResponseResult.error("班级编号被占用，请换一个");
            }
        }
        return classService.saveOrUpdate(oiClass) ? ResponseResult.ok("保存成功") : ResponseResult.ok("保存失败");
    }

    /**
     * 删除班级信息
     *
     * @param classId
     * @return
     */
    @DeleteMapping("remove")
    public ResponseResult removeById(Long classId) {
        return classService.deleteById(classId) ? ResponseResult.ok("删除成功") : ResponseResult.ok("删除失败");
    }
}
