package cn.njcit.service.impl;

import cn.njcit.entity.AttendsList;
import cn.njcit.mapper.AttendsListMapper;
import cn.njcit.service.IAttendsListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 考勤打卡 服务实现类
 * </p>
 *
 * @author selena
 * @since 2023-10-24
 */
@Service
public class AttendsListServiceImpl extends ServiceImpl<AttendsListMapper, AttendsList> implements IAttendsListService {

}
