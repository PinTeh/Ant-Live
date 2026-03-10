package cn.imhtb.live.service.impl;

import cn.imhtb.live.pojo.database.Withdrawal;
import cn.imhtb.live.mappers.WithdrawalMapper;
import cn.imhtb.live.service.IWithdrawalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author PinTeh
 * @date 2020/4/9
 */
@Service
public class WithdrawalServiceImpl extends ServiceImpl<WithdrawalMapper, Withdrawal> implements IWithdrawalService {
}
