package cn.imhtb.antlive.service;

import cn.imhtb.antlive.entity.database.StatisticSpeak;
import cn.imhtb.antlive.entity.database.StatisticView;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author PinTeh
 * @date 2020/4/11
 */
public interface IStatisticSpeakService extends IService<StatisticSpeak> {

    List<StatisticSpeak> listInDateRange(int days,Integer rid);

}
