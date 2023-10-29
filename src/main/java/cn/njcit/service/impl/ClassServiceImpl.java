package cn.njcit.service.impl;

import cn.njcit.entity.OIClass;
import cn.njcit.mapper.ClassMapper;
import cn.njcit.service.IClassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 班级信息 服务实现类
 * </p>
 *
 * @author selena
 * @since 2023-10-24
 */
@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, OIClass> implements IClassService {
    private final int PAGE_SIZE = 5;// 每页展示的条数

    @Override
    public PageInfo<OIClass> getClassList(Integer page, String searchName) {
        PageHelper.startPage(page, PAGE_SIZE);// 设置起始页，次此返回第几页
        List<OIClass> classList = null;
        if (searchName == null || searchName.trim().equals("")) {// searchName为null或者空字符串，查询所有班级
            classList = baseMapper.getAllClasses();// 注意此处的baseMapper就是UserMapper，已经注入，可以直接用
        } else {// searchName不为null，按照班级名称查询班级
            classList = baseMapper.searchClasses(searchName);
        }
        return new PageInfo<OIClass>(classList);// 将查询到的班级列表包装成分页对象
    }

    @Override
    public OIClass getClassById(Long id) {
        OIClass oiClass = baseMapper.getClassById(id);
        return oiClass;
    }

    @Override
    public Boolean deleteById(Long id) {
        return baseMapper.deleteById(id);
    }
}
