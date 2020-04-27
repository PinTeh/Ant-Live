package cn.imhtb.antlive.service.impl;

import cn.imhtb.antlive.entity.database.LiveDetect;
import cn.imhtb.antlive.mappers.LiveDetectMapper;
import cn.imhtb.antlive.service.ILiveDetectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author PinTeh
 * @date 2020/4/15
 */
@Service
public class LiveDetectServiceImpl extends ServiceImpl<LiveDetectMapper, LiveDetect> implements ILiveDetectService {
}
