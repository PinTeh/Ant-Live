package cn.imhtb.live.modules.live.service.impl;

import cn.imhtb.live.modules.live.vo.LiveInfoReqVo;
import cn.imhtb.live.pojo.database.LiveInfo;
import cn.imhtb.live.mappers.LiveInfoMapper;
import cn.imhtb.live.modules.live.service.ILiveInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author PinTeh
 * @date 2020/3/5
 */
@Service
public class LiveInfoServiceImpl extends ServiceImpl<LiveInfoMapper, LiveInfo> implements ILiveInfoService {



    @Override
    public void getLiveRecords(LiveInfoReqVo liveInfoReqVo, Integer userId) {

    }
}
