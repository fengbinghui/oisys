package cn.njcit.mapper;

import cn.njcit.entity.Role;
import cn.njcit.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {
    List<Role> searchRoles(@Param("searchName") String searchName);

    List<SysMenu> getMenusByRoleId(@Param("roleId") Long roleId);
}
