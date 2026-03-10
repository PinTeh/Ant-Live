package cn.imhtb.live.modules.live.service;

import cn.imhtb.live.modules.live.vo.LiveInfoReqVo;
import cn.imhtb.live.pojo.database.LiveInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author PinTeh
 * @date 2020/3/5
 */
public interface ILiveInfoService extends IService<LiveInfo> {

    void getLiveRecords(LiveInfoReqVo liveInfoReqVo, Integer userId);

}
