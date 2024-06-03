package cn.imhtb.live.mappers;

import cn.imhtb.live.pojo.Bill;
import cn.imhtb.live.pojo.vo.response.BillTotalResponse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BillMapper extends BaseMapper<Bill> {

    @Select("select sum(bill_change) from bill where mark = '充值'")
    Integer countIncome();

    @Select("select abs(sum(bill_change)) from bill where mark = '提现'")
    Integer countOutlay();

    @Select("select abs(sum(bill_change)) from bill where type = 1")
    Integer countBill();

    @Select("select abs(sum(bill_change)) from bill where mark = '充值' and date(create_time) = curdate()")
    Integer incomeToday();

    @Select("select abs(sum(bill_change)) from bill where mark = '提现' and date(create_time) = curdate()")
    Integer outlayToday();

    @Select("select abs(sum(bill_change)) from bill where type = 1 and date(create_time) = curdate()")
    Integer billToday();

    @Select("select abs(sum(bill_change)) number,DATE_FORMAT(create_time, '%Y-%m-%d') date from bill  where date(create_time) >= date(#{startTime}) and date(create_time) <=date(#{endTime}) and mark='充值'  group by DATE_FORMAT(create_time, '%Y-%m-%d')")
    List<BillTotalResponse> billTotalIncome(String startTime, String endTime);

    @Select("select abs(sum(bill_change)) number,DATE_FORMAT(create_time, '%Y-%m-%d') date from bill  where date(create_time) >= date(#{startTime}) and date(create_time) <=date(#{endTime}) and mark='提现'  group by DATE_FORMAT(create_time, '%Y-%m-%d')")
    List<BillTotalResponse> billTotalOutlay(String startTime, String endTime);
}
