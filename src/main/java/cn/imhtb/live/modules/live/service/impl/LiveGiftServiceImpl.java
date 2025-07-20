package cn.imhtb.live.modules.live.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.imhtb.live.common.PageData;
import cn.imhtb.live.common.enums.StatusEnum;
import cn.imhtb.live.common.exception.BusinessException;
import cn.imhtb.live.common.holder.UserHolder;
import cn.imhtb.live.mappers.*;
import cn.imhtb.live.modules.live.service.ILiveGiftService;
import cn.imhtb.live.modules.live.vo.PresentRespVo;
import cn.imhtb.live.modules.live.vo.RewardReqVo;
import cn.imhtb.live.modules.live.vo.RewardRespVo;
import cn.imhtb.live.modules.server.netty.service.IRoomChatService;
import cn.imhtb.live.pojo.database.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author pinteh
 * @date 2025/7/13
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LiveGiftServiceImpl implements ILiveGiftService {

    private final UserMapper userMapper;
    private final RoomMapper roomMapper;
    private final WalletMapper walletMapper;
    private final PresentMapper presentMapper;
    private final IRoomChatService roomChatService;
    private final PresentRewardMapper presentRewardMapper;

    @Override
    public List<PresentRespVo> list() {
        LambdaQueryWrapper<Present> wrapper = new LambdaQueryWrapper<Present>()
                .eq(Present::getStatus, StatusEnum.YES.getCode())
                .orderByAsc(Present::getSort);
        List<Present> presents = presentMapper.selectList(wrapper);
        return BeanUtil.copyToList(presents, PresentRespVo.class);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createReward(RewardReqVo req) {
        Integer userId = UserHolder.getUserId();
        Present present = presentMapper.selectById(req.getPresentId());
        if (present == null){
            throw new BusinessException("礼物不存在");
        }
        // 获取钱包信息
        Wallet wallet = walletMapper.selectOne(new LambdaQueryWrapper<Wallet>()
                .eq(Wallet::getUserId, userId).last("for update"), false);
        BigDecimal totalPrice = present.getPrice().multiply(new BigDecimal(String.valueOf(req.getNumber())));
        if (totalPrice.compareTo(wallet.getBalance()) > 0){
            throw new BusinessException("余额不足");
        }
        // 业务校验
        User user = userMapper.selectById(userId);
        Room room = roomMapper.selectById(req.getRoomId());
        if (room == null){
            throw new BusinessException("直播间不存在");
        }
        if(room.getUserId().equals(userId)){
            throw new BusinessException("不能自己给自己送礼物");
        }
        // 记录赠送记录
        PresentReward presentReward = new PresentReward();
        presentReward.setFromId(userId);
        presentReward.setToId(room.getUserId());
        presentReward.setRoomId(req.getRoomId());
        presentReward.setUnitPrice(present.getPrice());
        presentReward.setPresentId(req.getPresentId());
        presentReward.setNumber(req.getNumber());
        presentReward.setTotalPrice(totalPrice);
        presentRewardMapper.insert(presentReward);
        // 发送ws消息
        String msgFormat = "%s赠送了%s * %d";
        String text = String.format(msgFormat, user.getNickname(), present.getName(), req.getNumber());
        roomChatService.sendGiftMsg(text, room.getId(), userId, req.getPresentId());
    }

    @Override
    public PageData<RewardRespVo> rewardList(Integer page, Integer pageSize) {
        Page<RewardRespVo> pageParam = new Page<>(page, pageSize);
        QueryWrapper<RewardRespVo> wrapper = new QueryWrapper<>();
        wrapper.eq("pr.to_id", UserHolder.getUserId());
        Page<RewardRespVo> res = presentRewardMapper.pageRewardRespVo(pageParam, wrapper);
        PageData<RewardRespVo> pageData = new PageData<>();
        pageData.setList(res.getRecords());
        pageData.setTotal(res.getTotal());
        return pageData;
    }

}
