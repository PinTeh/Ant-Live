package cn.imhtb.live.modules.wallet.controller;

import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.common.PageData;
import cn.imhtb.live.common.holder.UserHolder;
import cn.imhtb.live.modules.wallet.model.RechargeReq;
import cn.imhtb.live.modules.wallet.model.WalletLogResp;
import cn.imhtb.live.modules.wallet.service.IWalletLogService;
import cn.imhtb.live.modules.wallet.service.IWalletService;
import cn.imhtb.live.pojo.database.Wallet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

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
        Wallet wallet = walletService.getWallet(UserHolder.getUserId());
        return ApiResponse.ofSuccess(wallet);
    }

    @ApiOperation("获取钱包最近的变化记录")
    @GetMapping("/listRecentWalletLogs")
    public ApiResponse<?> listRecentWalletLogs(){
        PageData<WalletLogResp> pageData = walletLogService.listRecentWalletLogs(UserHolder.getUserId());
        return ApiResponse.ofSuccess(pageData);
    }

    @ApiOperation("获取钱包变化记录")
    @GetMapping("/listWalletLogs")
    public ApiResponse<PageData<WalletLogResp>> listWalletLogs(@RequestParam Integer pageNo, @RequestParam Integer pageSize){
        PageData<WalletLogResp> pageData = walletLogService.listWalletLogs(UserHolder.getUserId(), pageNo, pageSize);
        return ApiResponse.ofSuccess(pageData);
    }

    @ApiOperation("模拟充值")
    @PostMapping("/recharge")
    public ApiResponse<Boolean> recharge(@RequestBody RechargeReq rechargeReq){
        return ApiResponse.ofSuccess(walletService.rechargeMock(UserHolder.getUserId(), rechargeReq.getFee()));
    }

}
