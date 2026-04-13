package cn.njcit.mapper;

import cn.njcit.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysMenuMapper extends BaseMapper<SysMenu> {
    List<SysMenu> searchMenus(@Param("searchName") String searchName);

    List<SysMenu> getMenusByRoleId(@Param("roleId") Long roleId);
}
