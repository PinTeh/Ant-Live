package cn.imhtb.antlive.controller.admin;

import cn.imhtb.antlive.common.ApiResponse;
import cn.imhtb.antlive.entity.Bill;
import cn.imhtb.antlive.entity.LiveInfo;
import cn.imhtb.antlive.entity.Room;
import cn.imhtb.antlive.service.IBillService;
import cn.imhtb.antlive.service.ILiveInfoService;
import cn.imhtb.antlive.service.IRoomService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author PinTeh
 * @date 2020/3/26
 */
@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAnyRole('ROLE_ROOT','ROLE_LIVE')")
public class AdminController {

    private final IRoomService roomService;

    private final IBillService billService;

    private final ILiveInfoService liveInfoService;

    public AdminController(IRoomService roomService, IBillService billService, ILiveInfoService liveInfoService ) {
        this.roomService = roomService;
        this.billService = billService;
        this.liveInfoService = liveInfoService;
    }


    @GetMapping("/room/list")
    public ApiResponse roomList(
            @RequestParam(required = false) Integer rid,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Integer disabled,
            @RequestParam(defaultValue = "1",required = false) Integer page,@RequestParam(defaultValue = "10",required = false) Integer limit){
        IPage<Room> iPage = roomService.page(new Page<>(page,limit), new QueryWrapper<Room>()
                .eq(status!=null,"status",status)
                .eq(disabled!=null,"disabled",disabled)
                .eq(rid!=null,"id",rid).orderByDesc("id"));
        return ApiResponse.ofSuccess(iPage);
    }

    @GetMapping("/liveInfo/list")
    public ApiResponse liveInfoList(@RequestParam(defaultValue = "1",required = false) Integer page,@RequestParam(defaultValue = "10",required = false) Integer limit,
                                    @RequestParam(required = false) Integer rid,@RequestParam(required = false) String dateRange){
        String maxTime = "",minTime = "";
        boolean condition = !StringUtils.isEmpty(dateRange) && !"null".equals(dateRange);
        if (condition) {
            maxTime = dateRange.split(",")[1];
            minTime = dateRange.split(",")[0];
        }
        IPage<LiveInfo> iPage = liveInfoService.page(new Page<>(page,limit), new QueryWrapper<LiveInfo>()
                .eq(rid!=null,"room_id",rid)
                .le(condition,"create_time",maxTime)
                .ge(condition,"create_time",minTime)
                .orderByDesc("id"));
        return ApiResponse.ofSuccess(iPage);
    }

    @GetMapping("/bill/list")
    public ApiResponse billList(@RequestParam(defaultValue = "1",required = false) Integer page,@RequestParam(defaultValue = "10",required = false) Integer limit,
                                @RequestParam(required = false) Integer type,
                                @RequestParam(required = false) String mark,
                                @RequestParam(required = false) String dateRange){
        String maxTime = "",minTime = "";
        boolean condition = !StringUtils.isEmpty(dateRange) && !"null".equals(dateRange);
        if (condition) {
            maxTime = dateRange.split(",")[1];
            minTime = dateRange.split(",")[0];
        }
        IPage<Bill> iPage = billService.page(new Page<>(page,limit), new QueryWrapper<Bill>()
                .eq(type!=null,"type",type)
                .eq(!StringUtils.isEmpty(mark),"mark",mark)
                .le(condition,"create_time",maxTime)
                .ge(condition,"create_time",minTime)
                 .orderByDesc("id"));
        return ApiResponse.ofSuccess(iPage);
    }

}
