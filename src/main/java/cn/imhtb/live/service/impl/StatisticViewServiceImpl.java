package cn.imhtb.live.service.impl;

import cn.imhtb.live.pojo.database.StatisticView;
import cn.imhtb.live.mappers.StatisticViewMapper;
import cn.imhtb.live.service.IStatisticViewService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author PinTeh
 * @date 2020/4/11
 */
@Service
public class StatisticViewServiceImpl extends ServiceImpl<StatisticViewMapper, StatisticView> implements IStatisticViewService {

    private final StatisticViewMapper statisticViewMapper;

    public StatisticViewServiceImpl(StatisticViewMapper statisticViewMapper) {
        this.statisticViewMapper = statisticViewMapper;
    }

    @Override
    public List<StatisticView> listInDateRange(int days, Integer rid) {
        return statisticViewMapper.listInDateRange(days, rid);
    }
}
