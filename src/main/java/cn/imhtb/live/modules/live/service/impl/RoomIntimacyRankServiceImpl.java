package cn.imhtb.live.modules.live.service.impl;

import cn.imhtb.live.mappers.RoomIntimacyRankMapper;
import cn.imhtb.live.modules.live.service.IRoomIntimacyRankService;
import cn.imhtb.live.modules.live.vo.RoomIntimacyRankRespVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class RoomIntimacyRankServiceImpl implements IRoomIntimacyRankService {

    private static final int RANK_LIMIT = 10;

    private final RoomIntimacyRankMapper roomIntimacyRankMapper;

    public RoomIntimacyRankServiceImpl(RoomIntimacyRankMapper roomIntimacyRankMapper) {
        this.roomIntimacyRankMapper = roomIntimacyRankMapper;
    }

    @Override
    public void addChatIntimacy(Integer roomId, Integer userId) {
        if (roomId == null || userId == null) {
            return;
        }
        roomIntimacyRankMapper.increase(roomId, userId, BigDecimal.ONE);
    }

    @Override
    public void addGiftIntimacy(Integer roomId, Integer userId, BigDecimal intimacyValue) {
        if (roomId == null || userId == null || intimacyValue == null || intimacyValue.compareTo(BigDecimal.ZERO) <= 0) {
            return;
        }
        roomIntimacyRankMapper.increase(roomId, userId, intimacyValue);
    }

    @Override
    public List<RoomIntimacyRankRespVo> getTopRanks(Integer roomId) {
        List<RoomIntimacyRankRespVo> ranks = roomIntimacyRankMapper.selectTopByRoomId(roomId, RANK_LIMIT);
        for (int i = 0; i < ranks.size(); i++) {
            ranks.get(i).setRankNo(i + 1);
        }
        return ranks;
    }

    @Override
    @Scheduled(cron = "0 0 0 1 * ?")
    public void clearMonthlyRanks() {
        int count = roomIntimacyRankMapper.clearAll();
        log.info("clear room intimacy ranks success, affected rows: {}", count);
    }
}
