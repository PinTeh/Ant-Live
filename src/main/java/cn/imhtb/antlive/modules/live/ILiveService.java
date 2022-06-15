package cn.imhtb.antlive.modules.live;

import cn.imhtb.antlive.pojo.LiveStatusVo;
import cn.imhtb.antlive.pojo.StartOpenLiveVo;

import javax.servlet.http.HttpServletRequest;

public interface ILiveService {

    /**
     * 申请秘密
     *
     * @param request 请求
     * @return {@link StartOpenLiveVo}
     */
    StartOpenLiveVo applySecret(HttpServletRequest request);

    /**
     * 停止直播
     *
     * @param request 请求
     */
    void stopLive(HttpServletRequest request);

    LiveStatusVo getLiveStatus(HttpServletRequest request);

}
