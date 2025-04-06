package cn.imhtb.live.modules.wallet.service;

import cn.imhtb.live.common.PageData;
import cn.imhtb.live.pojo.database.WalletLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author pinteh
 * @date 2025/4/2
 */
public interface IWalletLogService extends IService<WalletLog> {

    /**
     * 获取钱包最近的记录
     * @param userId 当前用户id
     * @return 分页记录
     */
    PageData<WalletLog> listRecentWalletLogs(Integer userId);

    /**
     * 获取钱包记录
     * @param userId 当前用户id
     * @param pageNo 页码
     * @param pageSize 页面大小
     * @return 分页记录
     */
    PageData<WalletLog> listWalletLogs(Integer userId, Integer pageNo, Integer pageSize);

}
