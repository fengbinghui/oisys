package cn.njcit.service.impl;

import cn.njcit.entity.SysMenu;
import cn.njcit.mapper.SysMenuMapper;
import cn.njcit.service.ISysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {
    private static final int PAGE_SIZE = 10;

    @Override
    public PageInfo<SysMenu> getMenuList(Integer page, String searchName) {
        PageHelper.startPage(page, PAGE_SIZE);
        List<SysMenu> menus;
        if (searchName == null || searchName.trim().isEmpty()) {
            menus = baseMapper.selectList(null);
        } else {
            menus = baseMapper.searchMenus(searchName);
        }
        return new PageInfo<>(menus);
    }

    @Override
    public List<SysMenu> getMenusByRoleId(Long roleId) {
        return baseMapper.getMenusByRoleId(roleId);
    }
}
