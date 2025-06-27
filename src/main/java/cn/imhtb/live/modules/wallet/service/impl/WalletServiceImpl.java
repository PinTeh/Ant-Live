package cn.imhtb.live.modules.wallet.service.impl;

import cn.imhtb.live.mappers.WalletLogMapper;
import cn.imhtb.live.mappers.WalletMapper;
import cn.imhtb.live.modules.wallet.service.IWalletService;
import cn.imhtb.live.pojo.database.Wallet;
import cn.imhtb.live.pojo.database.WalletLog;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author pinteh
 * @date 2025/4/2
 */
@Slf4j
@Service
public class WalletServiceImpl extends ServiceImpl<WalletMapper, Wallet> implements IWalletService {

    @Resource
    private WalletLogMapper walletLogMapper;

    @Override
    public Wallet getWallet(Integer userId) {
        Wallet wallet = lambdaQuery().eq(Wallet::getUserId, userId).one();
        if (wallet == null) {
            // 延迟方式初始化用户钱包
            return delayInitUserWallet(userId);
        }
        return wallet;
    }

    @Override
    public boolean decrease(Integer userId, BigDecimal fee) {
        Wallet wallet = this.getWallet(userId);
        return lambdaUpdate()
                .setSql("balance = balance - " + fee)
                .eq(Wallet::getBalance, wallet.getBalance())
                .eq(Wallet::getId, wallet.getId()).update();
    }

    @Override
    public boolean increase(Integer userId, BigDecimal fee) {
        Wallet wallet = this.getWallet(userId);
        return lambdaUpdate()
                .setSql("balance = balance + " + fee)
                .eq(Wallet::getBalance, wallet.getBalance())
                .eq(Wallet::getId, wallet.getId()).update();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean rechargeMock(Integer userId, String count) {
        Wallet wallet = this.getWallet(userId);
        BigDecimal rechargeCount = new BigDecimal(count);
        BigDecimal newCount = wallet.getBalance().add(rechargeCount);

        Wrapper<Wallet> wrapper = new LambdaUpdateWrapper<Wallet>()
                .eq(Wallet::getId, wallet.getId())
                .eq(Wallet::getVersion, wallet.getVersion())
                .set(Wallet::getBalance, newCount)
                .set(Wallet::getVersion, wallet.getVersion() + 1);

        boolean update = update(wrapper);
        if (update){
            WalletLog walletLog = new WalletLog();
            walletLog.setFee(rechargeCount);
            walletLog.setBalance(newCount);
            walletLog.setWalletId(wallet.getId());
            walletLog.setActionType(1);
            walletLogMapper.insert(walletLog);
            return true;
        }
        return false;
    }

    /**
     * 延迟初始化用户钱包
     *
     * @param userId 用户id
     */
    private Wallet delayInitUserWallet(Integer userId) {
        try {
            Wallet wallet = new Wallet();
            wallet.setUserId(userId);
            wallet.setBalance(BigDecimal.ZERO);
            wallet.setStatus(0);
            save(wallet);
            return wallet;
        } catch (Exception e) {
            log.warn("init user wallet error, userId = {}", userId);
            return this.getWallet(userId);
        }
    }

}
