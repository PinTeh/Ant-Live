package cn.imhtb.antlive.service.impl;

import cn.imhtb.antlive.entity.LiveInfo;
import cn.imhtb.antlive.mappers.LiveInfoMapper;
import cn.imhtb.antlive.service.ILiveInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author PinTeh
 * @date 2020/3/5
 */
@Service
public class LiveInfoServiceImpl extends ServiceImpl<LiveInfoMapper, LiveInfo> implements ILiveInfoService {

}
