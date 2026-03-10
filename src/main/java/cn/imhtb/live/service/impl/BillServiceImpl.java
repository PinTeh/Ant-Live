package cn.imhtb.live.service.impl;

import cn.imhtb.live.mappers.BillMapper;
import cn.imhtb.live.pojo.database.Bill;
import cn.imhtb.live.pojo.vo.response.BillTotalResponse;
import cn.imhtb.live.service.IBillService;
import cn.imhtb.live.service.ITokenService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BillServiceImpl extends ServiceImpl<BillMapper, Bill> implements IBillService {

    private final ITokenService tokenService;
    private final BillMapper billMapper;

    @Override
    public BigDecimal getBalance(Integer userId) {
        if (Objects.isNull(userId)){
            userId = tokenService.getUserId();
        }
        List<Bill> bills = lambdaQuery().eq(Bill::getUserId, userId)
                .orderByDesc(Bill::getId)
                .last("limit 0, 1").list();
        if (CollectionUtils.isEmpty(bills)){
            return BigDecimal.ZERO;
        }
        return bills.get(0).getBalance();
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
        return billMapper.billTotalIncome(startTime, endTime);
    }

    @Override
    public List<BillTotalResponse> billOutlay(String startTime, String endTime) {
        return billMapper.billTotalOutlay(startTime, endTime);
    }
}
