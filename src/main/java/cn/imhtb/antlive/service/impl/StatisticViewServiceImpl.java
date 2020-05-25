package cn.imhtb.antlive.service.impl;

import cn.imhtb.antlive.entity.database.StatisticView;
import cn.imhtb.antlive.mappers.StatisticViewMapper;
import cn.imhtb.antlive.service.IStatisticViewService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    public List<StatisticView> listInDateRange(int days,Integer rid) {
        return statisticViewMapper.listInDateRange(days,rid);
    }
}
