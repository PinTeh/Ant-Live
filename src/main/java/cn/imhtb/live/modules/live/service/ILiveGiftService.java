package cn.imhtb.live.modules.live.service;

import cn.imhtb.live.common.PageData;
import cn.imhtb.live.modules.live.vo.PresentRespVo;
import cn.imhtb.live.modules.live.vo.RewardReqVo;
import cn.imhtb.live.modules.live.vo.RewardRespVo;

import java.util.List;

/**
 * @author pinteh
 * @date 2025/7/13
 */
public interface ILiveGiftService {

    /**
     * 获取礼物列表
     *
     * @return 礼物列表
     */
    List<PresentRespVo> list();

    /**
     * 礼物赠送
     *
     * @param rewardReqVo *
     */
    void createReward(RewardReqVo rewardReqVo);

    /**
     * 礼物赠送记录
     *
     * @param page 页码
     * @param pageSize 分页大小
     * @return 分页数据
     */
    PageData<RewardRespVo> rewardList(Integer page, Integer pageSize);

}
