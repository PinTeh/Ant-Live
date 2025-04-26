package cn.imhtb.live.modules.wallet.controller;

import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.common.PageData;
import cn.imhtb.live.common.holder.UserHolder;
import cn.imhtb.live.modules.wallet.service.IWalletLogService;
import cn.imhtb.live.modules.wallet.service.IWalletService;
import cn.imhtb.live.pojo.database.Wallet;
import cn.imhtb.live.pojo.database.WalletLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author pinteh
 * @date 2025/4/2
 */
@Api(tags = "钱包接口")
@RestController
@RequestMapping("/api/v1/wallet")
public class WalletController {

    @Resource
    private IWalletService walletService;
    @Resource
    private IWalletLogService walletLogService;

    @ApiOperation("获取钱包")
    @GetMapping("/getBalance")
    public ApiResponse<Wallet> getBalance(){
        Integer userId = UserHolder.getUserId();
        Wallet wallet = walletService.getWallet(userId);
        return ApiResponse.ofSuccess(wallet);
    }

    @ApiOperation("获取钱包最近的变化记录")
    @GetMapping("/listRecentWalletLogs")
    public ApiResponse<?> listRecentWalletLogs(){
        Integer userId = UserHolder.getUserId();
        PageData<WalletLog> pageData = walletLogService.listRecentWalletLogs(userId);
        return ApiResponse.ofSuccess(pageData);
    }

    @ApiOperation("获取钱包变化记录")
    @GetMapping("/listWalletLogs")
    public ApiResponse<?> listWalletLogs(@RequestParam Integer pageNo, @RequestParam Integer pageSize){
        Integer userId = UserHolder.getUserId();
        PageData<WalletLog> pageData = walletLogService.listWalletLogs(userId, pageNo, pageSize);
        return ApiResponse.ofSuccess(pageData);
    }

}
