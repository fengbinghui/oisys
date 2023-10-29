package cn.njcit.service;

import cn.njcit.entity.OIClass;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 班级信息 服务类
 * </p>
 *
 * @author selena
 * @since 2023-10-24
 */
public interface IClassService extends IService<OIClass> {
    public PageInfo<OIClass> getClassList(Integer page, String searchName);

    //按照id查询班级
    public OIClass getClassById(Long id);

    //按照id删除班级
    public Boolean deleteById(Long id);
}
