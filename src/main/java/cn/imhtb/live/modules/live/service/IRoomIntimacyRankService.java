package cn.imhtb.live.modules.live.service;

import cn.imhtb.live.modules.live.vo.RoomIntimacyRankRespVo;

import java.math.BigDecimal;
import java.util.List;

public interface IRoomIntimacyRankService {

    /**
     * 发送弹幕增加亲密值
     */
    void addChatIntimacy(Integer roomId, Integer userId);

    /**
     * 送礼增加亲密值
     */
    void addGiftIntimacy(Integer roomId, Integer userId, BigDecimal intimacyValue);

    /**
     * 获取直播间亲密榜前十
     */
    List<RoomIntimacyRankRespVo> getTopRanks(Integer roomId);

    /**
     * 月初清空亲密榜
     */
    void clearMonthlyRanks();
}
