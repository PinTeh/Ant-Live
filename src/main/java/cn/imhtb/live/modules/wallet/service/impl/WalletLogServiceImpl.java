package cn.imhtb.live.modules.wallet.service.impl;

import cn.imhtb.live.common.PageData;
import cn.imhtb.live.mappers.WalletLogMapper;
import cn.imhtb.live.modules.wallet.service.IWalletLogService;
import cn.imhtb.live.modules.wallet.service.IWalletService;
import cn.imhtb.live.pojo.database.Wallet;
import cn.imhtb.live.pojo.database.WalletLog;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author pinteh
 * @date 2025/4/2
 */
@Slf4j
@Service
public class WalletLogServiceImpl extends ServiceImpl<WalletLogMapper, WalletLog> implements IWalletLogService {

    @Resource
    private IWalletService walletService;

    @Override
    public PageData<WalletLog> listRecentWalletLogs(Integer userId) {
        return this.listWalletLogs(userId, 1, 10);
    }

    @Override
    public PageData<WalletLog> listWalletLogs(Integer userId, Integer pageNo, Integer pageSize) {
        Wallet wallet = walletService.getWallet(userId);
        Page<WalletLog> page = lambdaQuery()
                .eq(WalletLog::getWalletId, wallet.getId())
                .orderByDesc(WalletLog::getCreateTime)
                .page(new Page<>(pageNo, pageSize));

        PageData<WalletLog> ans = new PageData<>();
        ans.setTotal(page.getTotal());
        ans.setList(page.getRecords());
        return ans;
    }

}
