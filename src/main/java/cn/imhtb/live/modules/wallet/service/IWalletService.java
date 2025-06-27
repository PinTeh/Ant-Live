package cn.imhtb.live.modules.wallet.service;

import cn.imhtb.live.pojo.database.Wallet;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

/**
 * @author pinteh
 * @date 2025/4/2
 */
public interface IWalletService extends IService<Wallet> {

    /**
     * 获取钱包
     * @return 钱包
     */
    Wallet getWallet(Integer userId);

    boolean decrease(Integer userId, BigDecimal fee);

    boolean increase(Integer userId, BigDecimal fee);

    /**
     * 模拟充值
     * @param userId 用户标识
     * @param count 充值数量
     * @return 充值结果
     */
    Boolean rechargeMock(Integer userId, String count);

}
