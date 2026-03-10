package cn.imhtb.live.mappers;

import cn.imhtb.live.pojo.database.StatisticView;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author PinTeh
 * @date 2020/4/11
 */
public interface StatisticViewMapper extends BaseMapper<StatisticView> {

    @Select("select sum(member_number) member_number,sum(visitor_number) visitor_number,sum(total_number) total_number,DATE_FORMAT(date, '%Y-%m-%d') date from statistic_view  where DATE_SUB(CURDATE(), INTERVAL #{days} DAY) <= date(date) and room_id = #{rid} group by DATE_FORMAT(date, '%Y-%m-%d')")
    List<StatisticView> listInDateRange(int days, Integer rid);
}
