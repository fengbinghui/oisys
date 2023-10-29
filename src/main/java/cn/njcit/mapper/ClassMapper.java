package cn.njcit.mapper;

import cn.njcit.entity.OIClass;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 班级信息 Mapper 接口
 * </p>
 *
 * @author selena
 * @since 2023-10-24
 */
public interface ClassMapper extends BaseMapper<OIClass> {
    // 查询所有班级
    public List<OIClass> getAllClasses();

    // 按照班级名称搜索班级（模糊查询）
    public List<OIClass> searchClasses(String searchName);

    // 按照班id查询班级
    public OIClass getClassById(Long id);

    // 按照id删除班级
    public Boolean deleteById(Long id);
}
