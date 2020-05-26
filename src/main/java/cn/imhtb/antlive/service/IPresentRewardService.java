package cn.imhtb.antlive.service;

import cn.imhtb.antlive.entity.database.PresentReward;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author PinTeh
 * @date 2020/3/25
 */
public interface IPresentRewardService extends IService<PresentReward> {

    boolean createReward(Integer fromId, Integer presentId, Integer rvId, Integer number,Integer type);

}
