package cn.njcit.service.impl;

import cn.njcit.entity.Role;
import cn.njcit.entity.SysMenu;
import cn.njcit.mapper.RoleMapper;
import cn.njcit.mapper.RolePowerMapper;
import cn.njcit.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    private final RolePowerMapper rolePowerMapper;
    private static final int PAGE_SIZE = 10;

    public RoleServiceImpl(RolePowerMapper rolePowerMapper) {
        this.rolePowerMapper = rolePowerMapper;
    }

    @Override
    public PageInfo<Role> getRoleList(Integer page, String searchName) {
        PageHelper.startPage(page, PAGE_SIZE);
        List<Role> roles;
        if (searchName == null || searchName.trim().isEmpty()) {
            roles = baseMapper.selectList(null);
        } else {
            roles = baseMapper.searchRoles(searchName);
        }
        return new PageInfo<>(roles);
    }

    @Override
    public List<SysMenu> getMenusByRoleId(Long roleId) {
        return baseMapper.getMenusByRoleId(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveRoleMenus(Long roleId, List<Long> menuIds) {
        rolePowerMapper.deleteByRoleId(roleId);
        if (menuIds == null || menuIds.isEmpty()) {
            return true;
        }
        return rolePowerMapper.batchInsert(roleId, menuIds) > 0;
    }
}
