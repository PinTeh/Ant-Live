package cn.imhtb.live.service.impl;

import cn.imhtb.live.pojo.database.LiveDetect;
import cn.imhtb.live.mappers.LiveDetectMapper;
import cn.imhtb.live.service.ILiveDetectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author PinTeh
 * @date 2020/4/15
 */
@Service
public class LiveDetectServiceImpl extends ServiceImpl<LiveDetectMapper, LiveDetect> implements ILiveDetectService {
}
