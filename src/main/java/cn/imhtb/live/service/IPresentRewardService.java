package cn.imhtb.live.service;

import cn.imhtb.live.pojo.database.PresentReward;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author PinTeh
 * @date 2020/3/25
 */
public interface IPresentRewardService extends IService<PresentReward> {

    /**
     * 创建奖励
     *
     * @param presentId *
     * @param rvId      *
     * @param number    数量
     * @param type      类型
     * @return boolean
     */
    String createReward(Integer presentId, Integer rvId, Integer number, Integer type);

}
