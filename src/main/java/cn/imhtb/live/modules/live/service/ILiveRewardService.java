package cn.imhtb.live.modules.live.service;

import cn.imhtb.live.modules.live.vo.RewardReqVo;

/**
 * @author pinteh
 * @date 2024/9/20
 */
public interface ILiveRewardService {

    /**
     * 礼物赠送
     *
     * @param rewardReqVo *
     */
    void createReward(RewardReqVo rewardReqVo);

}
