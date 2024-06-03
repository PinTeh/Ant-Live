package cn.imhtb.live.mappers;

import cn.imhtb.live.pojo.database.StatisticSpeak;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author PinTeh
 * @date 2020/4/11
 */
public interface StatisticSpeakMapper extends BaseMapper<StatisticSpeak> {

    @Select("select sum(number) number,DATE_FORMAT(date, '%Y-%m-%d') date from statistic_speak  where DATE_SUB(CURDATE(), INTERVAL #{days} DAY) <= date(date) and room_id = #{rid} group by DATE_FORMAT(date, '%Y-%m-%d')")
    List<StatisticSpeak> listInDateRange(int days, Integer rid);

}
