package cn.imhtb.live.modules.wallet.service.impl;

import cn.imhtb.live.mappers.WalletMapper;
import cn.imhtb.live.modules.wallet.service.IWalletService;
import cn.imhtb.live.pojo.database.Wallet;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

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
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Wallet getWallet(Integer userId) {
        Wallet wallet = lambdaQuery().eq(Wallet::getUserId, userId).one();
        if (wallet == null){
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
        }catch (Exception e){
            log.warn("init user wallet error, userId = {}", userId);
            return this.getWallet(userId);
        }
    }

}
