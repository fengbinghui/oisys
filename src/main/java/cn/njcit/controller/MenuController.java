package cn.njcit.controller;

import cn.njcit.entity.SysMenu;
import cn.njcit.service.ISysMenuService;
import cn.njcit.util.ResponseResult;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menuManage")
public class MenuController {
    private final ISysMenuService sysMenuService;

    public MenuController(ISysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }

    @GetMapping("/list")
    public ResponseResult list(@RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(required = false) String searchName) {
        PageInfo<SysMenu> menuList = sysMenuService.getMenuList(page, searchName);
        return ResponseResult.ok().put("menuList", menuList);
    }

    @GetMapping("/edit")
    public ResponseResult edit(@RequestParam(required = false) Long menuId) {
        ResponseResult result = ResponseResult.ok();
        if (menuId != null) {
            result.put("menu", sysMenuService.getById(menuId));
        }
        return result;
    }

    @PostMapping("/save")
    public ResponseResult save(SysMenu menu) {
        if (menu.getIsShow() == null) {
            menu.setIsShow(1);
        }
        boolean ok = sysMenuService.saveOrUpdate(menu);
        return ok ? ResponseResult.ok("保存成功") : ResponseResult.error("保存失败");
    }

    @DeleteMapping("/remove")
    public ResponseResult remove(Long menuId) {
        return sysMenuService.removeById(menuId) ? ResponseResult.ok("删除成功") : ResponseResult.error("删除失败");
    }
}
