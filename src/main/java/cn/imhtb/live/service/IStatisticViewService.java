package cn.imhtb.live.service;

import cn.imhtb.live.pojo.database.StatisticView;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author PinTeh
 * @date 2020/4/11
 */
public interface IStatisticViewService extends IService<StatisticView> {
    List<StatisticView> listInDateRange(int days, Integer rid);
}
