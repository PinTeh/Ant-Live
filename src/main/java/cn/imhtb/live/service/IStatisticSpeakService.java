package cn.imhtb.live.service;

import cn.imhtb.live.pojo.database.StatisticSpeak;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author PinTeh
 * @date 2020/4/11
 */
public interface IStatisticSpeakService extends IService<StatisticSpeak> {

    List<StatisticSpeak> listInDateRange(int days, Integer rid);

}
