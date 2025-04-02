package cn.imhtb.live.modules.live.service.impl;

import cn.imhtb.live.exception.BusinessException;
import cn.imhtb.live.exception.base.CommonErrorCode;
import cn.imhtb.live.holder.UserHolder;
import cn.imhtb.live.modules.live.service.ILiveRewardService;
import cn.imhtb.live.modules.live.service.IPresentService;
import cn.imhtb.live.modules.live.vo.RewardReqVo;
import cn.imhtb.live.pojo.Present;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author pinteh
 * @date 2024/9/20
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LiveRewardServiceImpl implements ILiveRewardService {

    private IPresentService presentService;

    @Override
    public void createReward(RewardReqVo rewardReqVo) {
        Integer userId = UserHolder.getUserId();
        // 获取礼物信息
        Present present = presentService.getById(rewardReqVo.getPresentId());
        if (present == null){
            throw new BusinessException(CommonErrorCode.SERVICE_ERROR);
        }
        // 计算总价
        BigDecimal totalPrice = present.getPrice().multiply(new BigDecimal(String.valueOf(rewardReqVo.getNumber())));
    }

}
