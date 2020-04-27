package cn.imhtb.antlive.mappers;

import cn.imhtb.antlive.entity.database.StatisticSpeak;
import cn.imhtb.antlive.entity.database.StatisticView;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author PinTeh
 * @date 2020/4/11
 */
public interface StatisticSpeakMapper extends BaseMapper<StatisticSpeak> {

    @Select("select sum(number) number,DATE_FORMAT(date, '%Y-%m-%d') date from tb_statistic_speak  where DATE_SUB(CURDATE(), INTERVAL #{days} DAY) <= date(date) group by DATE_FORMAT(date, '%Y-%m-%d')")
    List<StatisticSpeak> listInDateRange(int days);
}
