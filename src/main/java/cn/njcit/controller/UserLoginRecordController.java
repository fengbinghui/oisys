package cn.njcit.controller;

import cn.njcit.entity.UserLoginRecord;
import cn.njcit.service.IUserLoginRecordService;
import cn.njcit.util.ResponseResult;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author selena
 * @since 2023-10-24
 */
@RestController
@RequestMapping("/userLoginRecord")
public class UserLoginRecordController {
    private final IUserLoginRecordService userLoginRecordService;

    public UserLoginRecordController(IUserLoginRecordService userLoginRecordService) {
        this.userLoginRecordService = userLoginRecordService;
    }

    @GetMapping("/list")
    public ResponseResult list(@RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(required = false) String searchName) {
        PageInfo<UserLoginRecord> list = userLoginRecordService.getLoginRecordList(page, searchName);
        return ResponseResult.ok().put("recordList", list);
    }
}
