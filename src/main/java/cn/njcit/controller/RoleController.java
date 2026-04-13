package cn.njcit.controller;

import cn.njcit.entity.Role;
import cn.njcit.entity.SysMenu;
import cn.njcit.service.IRoleService;
import cn.njcit.service.ISysMenuService;
import cn.njcit.util.ResponseResult;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roleManage")
public class RoleController {
    private final IRoleService roleService;
    private final ISysMenuService sysMenuService;

    public RoleController(IRoleService roleService, ISysMenuService sysMenuService) {
        this.roleService = roleService;
        this.sysMenuService = sysMenuService;
    }

    @GetMapping("/list")
    public ResponseResult list(@RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(required = false) String searchName) {
        PageInfo<Role> roleList = roleService.getRoleList(page, searchName);
        return ResponseResult.ok().put("roleList", roleList);
    }

    @GetMapping("/edit")
    public ResponseResult edit(@RequestParam(required = false) Long roleId) {
        ResponseResult result = ResponseResult.ok();
        if (roleId != null) {
            result.put("role", roleService.getById(roleId));
        }
        return result;
    }

    @PostMapping("/save")
    public ResponseResult save(Role role) {
        boolean ok = roleService.saveOrUpdate(role);
        return ok ? ResponseResult.ok("保存成功").put("roleId", role.getRoleId()) : ResponseResult.error("保存失败");
    }

    @DeleteMapping("/remove")
    public ResponseResult remove(Long roleId) {
        return roleService.removeById(roleId) ? ResponseResult.ok("删除成功") : ResponseResult.error("删除失败");
    }

    @GetMapping("/menus")
    public ResponseResult roleMenus(@RequestParam Long roleId) {
        List<SysMenu> allMenus = sysMenuService.list();
        List<SysMenu> selectedMenus = roleService.getMenusByRoleId(roleId);
        return ResponseResult.ok().put("allMenus", allMenus).put("selectedMenus", selectedMenus);
    }

    @PostMapping("/saveMenus")
    public ResponseResult saveMenus(@RequestParam Long roleId,
                                    @RequestParam(required = false) List<Long> menuIds) {
        return roleService.saveRoleMenus(roleId, menuIds) ? ResponseResult.ok("分配成功") : ResponseResult.error("分配失败");
    }
}
