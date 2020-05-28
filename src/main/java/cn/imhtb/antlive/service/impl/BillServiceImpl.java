package cn.imhtb.antlive.service.impl;

import cn.imhtb.antlive.entity.Bill;
import cn.imhtb.antlive.mappers.BillMapper;
import cn.imhtb.antlive.service.IBillService;
import cn.imhtb.antlive.vo.response.BillTotalResponse;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillServiceImpl extends ServiceImpl<BillMapper, Bill> implements IBillService {

    private final BillMapper billMapper;

    public BillServiceImpl(BillMapper billMapper) {
        this.billMapper = billMapper;
    }

    @Override
    public Integer countIncome() {
        return billMapper.countIncome();
    }

    @Override
    public Integer countOutlay() {
        return billMapper.countOutlay();
    }

    @Override
    public Integer incomeToday() {
        return billMapper.incomeToday();
    }

    @Override
    public Integer outlayToday() {
        return billMapper.outlayToday();
    }

    @Override
    public Integer billToday() {
        return billMapper.billToday();
    }

    @Override
    public Integer countBill() {
        return billMapper.countBill();
    }

    @Override
    public List<BillTotalResponse> billIncome(String startTime, String endTime) {
        return billMapper.billTotalIncome(startTime,endTime);
    }

    @Override
    public List<BillTotalResponse> billOutlay(String startTime, String endTime) {
        return billMapper.billTotalOutlay(startTime,endTime);
    }
}
