package cn.imhtb.live.modules.live.service;

import cn.imhtb.live.pojo.LiveStatusVo;
import cn.imhtb.live.pojo.StartOpenLiveVo;

/**
 * @author pinteh
 */
public interface ILiveService {

    /**
     * 获取服务名称
     *
     * @return {@link String}
     */
    String getName();

    /**
     * 申请秘密
     *
     * @return {@link StartOpenLiveVo}
     */
    StartOpenLiveVo applySecret();

    /**
     * 停止直播
     *
     */
    void stopLive();

    /**
     * 获取直播状态
     *
     * @return {@link LiveStatusVo}
     */
    LiveStatusVo getLiveStatus();

}
