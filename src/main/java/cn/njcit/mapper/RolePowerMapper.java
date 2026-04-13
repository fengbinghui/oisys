package cn.njcit.mapper;

import cn.njcit.entity.RolePower;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolePowerMapper extends BaseMapper<RolePower> {
    int deleteByRoleId(@Param("roleId") Long roleId);

    int batchInsert(@Param("roleId") Long roleId, @Param("menuIds") List<Long> menuIds);
}
