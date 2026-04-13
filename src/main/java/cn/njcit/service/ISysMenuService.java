package cn.njcit.service;

import cn.njcit.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ISysMenuService extends IService<SysMenu> {
    PageInfo<SysMenu> getMenuList(Integer page, String searchName);

    List<SysMenu> getMenusByRoleId(Long roleId);
}
