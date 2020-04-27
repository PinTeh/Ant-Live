package cn.imhtb.antlive.service.impl;

import cn.imhtb.antlive.entity.database.Withdrawal;
import cn.imhtb.antlive.mappers.WithdrawalMapper;
import cn.imhtb.antlive.service.IWithdrawalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author PinTeh
 * @date 2020/4/9
 */
@Service
public class WithdrawalServiceImpl extends ServiceImpl<WithdrawalMapper, Withdrawal> implements IWithdrawalService {
}
