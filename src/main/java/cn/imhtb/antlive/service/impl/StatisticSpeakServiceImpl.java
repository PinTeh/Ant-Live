package cn.imhtb.antlive.service.impl;

import cn.imhtb.antlive.entity.database.StatisticSpeak;
import cn.imhtb.antlive.mappers.StatisticSpeakMapper;
import cn.imhtb.antlive.service.IStatisticSpeakService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author PinTeh
 * @date 2020/4/11
 */
@Service
public class StatisticSpeakServiceImpl extends ServiceImpl<StatisticSpeakMapper, StatisticSpeak> implements IStatisticSpeakService {

    private final StatisticSpeakMapper statisticSpeakMapper;

    public StatisticSpeakServiceImpl(StatisticSpeakMapper statisticSpeakMapper) {
        this.statisticSpeakMapper = statisticSpeakMapper;
    }

    @Override
    public List<StatisticSpeak> listInDateRange(int days) {
        return statisticSpeakMapper.listInDateRange(days);
    }
}
