package cn.njcit.service;

import cn.njcit.entity.Role;
import cn.njcit.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IRoleService extends IService<Role> {
    PageInfo<Role> getRoleList(Integer page, String searchName);

    List<SysMenu> getMenusByRoleId(Long roleId);

    boolean saveRoleMenus(Long roleId, List<Long> menuIds);
}
