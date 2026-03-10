package cn.imhtb.live.service;

import cn.imhtb.live.pojo.database.Bill;
import cn.imhtb.live.pojo.vo.response.BillTotalResponse;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.List;

public interface IBillService extends IService<Bill> {


    /**
     * 获取余额
     *
     * @param userId 用户id
     * @return {@link BigDecimal}
     */
    BigDecimal getBalance(Integer userId);

    Integer countIncome();

    Integer countOutlay();

    Integer incomeToday();

    Integer outlayToday();

    Integer billToday();

    Integer countBill();

    List<BillTotalResponse> billIncome(String startTime, String endTime);

    List<BillTotalResponse> billOutlay(String startTime, String endTime);
}
