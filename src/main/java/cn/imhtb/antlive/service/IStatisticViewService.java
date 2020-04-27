package cn.imhtb.antlive.service;

import cn.imhtb.antlive.entity.database.StatisticView;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author PinTeh
 * @date 2020/4/11
 */
public interface IStatisticViewService extends IService<StatisticView> {
    List<StatisticView> listInDateRange(int days);
}
