package cn.njcit.controller;

import cn.njcit.entity.AttendClassSetting;
import cn.njcit.service.IAttendClassSettingService;
import cn.njcit.service.ICourseManageService;
import cn.njcit.util.ResponseResult;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attendClassSetting")
public class AttendClassSettingController {
    private final IAttendClassSettingService attendClassSettingService;
    private final ICourseManageService courseManageService;

    public AttendClassSettingController(IAttendClassSettingService attendClassSettingService,
                                        ICourseManageService courseManageService) {
        this.attendClassSettingService = attendClassSettingService;
        this.courseManageService = courseManageService;
    }

    @GetMapping("/list")
    public ResponseResult list(@RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(required = false) String searchName) {
        PageInfo<AttendClassSetting> settingList = attendClassSettingService.getSettingList(page, searchName);
        return ResponseResult.ok().put("settingList", settingList);
    }

    @GetMapping("/edit")
    public ResponseResult edit(@RequestParam(required = false) Long id) {
        ResponseResult result = ResponseResult.ok();
        if (id != null) {
            result.put("setting", attendClassSettingService.getById(id));
        }
        result.put("courseManages", courseManageService.list());
        return result;
    }

    @PostMapping("/save")
    public ResponseResult save(AttendClassSetting setting) {
        if (setting.getDelFlag() == null) {
            setting.setDelFlag(0);
        }
        return attendClassSettingService.saveOrUpdate(setting) ? ResponseResult.ok("保存成功") : ResponseResult.error("保存失败");
    }

    @DeleteMapping("/remove")
    public ResponseResult remove(@RequestParam Long id) {
        AttendClassSetting setting = new AttendClassSetting();
        setting.setId(id);
        setting.setDelFlag(1);
        return attendClassSettingService.updateById(setting) ? ResponseResult.ok("删除成功") : ResponseResult.error("删除失败");
    }
}
