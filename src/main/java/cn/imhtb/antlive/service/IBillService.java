package cn.imhtb.antlive.service;

import cn.imhtb.antlive.entity.Bill;
import cn.imhtb.antlive.vo.response.BillTotalResponse;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IBillService extends IService<Bill> {

    Integer countIncome();

    Integer countOutlay();

    Integer incomeToday();

    Integer outlayToday();

    Integer billToday();

    Integer countBill();

    List<BillTotalResponse> billIncome(String startTime, String endTime);

    List<BillTotalResponse> billOutlay(String startTime, String endTime);
}
