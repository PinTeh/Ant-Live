package cn.imhtb.live.controller.admin;

import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.pojo.database.Room;
import cn.imhtb.live.common.enums.LiveRoomStatusEnum;
import cn.imhtb.live.service.IBillService;
import cn.imhtb.live.service.IRoomService;
import cn.imhtb.live.modules.user.service.IUserService;
import cn.imhtb.live.pojo.vo.response.BillTotalResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author PinTeh
 * @date 2020/5/27
 */
@RestController
@RequestMapping("/admin/total")
@PreAuthorize("hasAnyRole('ROLE_ROOT','ROLE_LIVE')")
public class AdminTotalController {

    private final IUserService userService;

    private final IRoomService roomService;

    private final IBillService billService;

    public AdminTotalController(IUserService userService, IBillService billService, IRoomService roomService) {
        this.userService = userService;
        this.billService = billService;
        this.roomService = roomService;
    }

    @GetMapping("/all/count")
    public ApiResponse allCount() {
        Map<String, Integer> map = new HashMap<>();
        map.put("user", (int) userService.count());
        map.put("room", (int) roomService.count());
        map.put("income", billService.countIncome());
        map.put("outlay", billService.countOutlay());
        map.put("bill", billService.countBill());
        return ApiResponse.ofSuccess(map);
    }

    @GetMapping("/today/count")
    public ApiResponse todayCount() {
        Map<String, Integer> map = new HashMap<>();
        map.put("livingRoom", (int) roomService.count(new QueryWrapper<Room>().eq("status", LiveRoomStatusEnum.LIVING.getCode())));
        map.put("userToday", userService.countToday());
        map.put("incomeToday", billService.incomeToday());
        map.put("outlayToday", billService.outlayToday());
        map.put("billToday", billService.billToday());
        return ApiResponse.ofSuccess(map);

    }


    @GetMapping("/bill/income")
    public ApiResponse billIncome(String dateRange) {
        String startTime = "", endTime = "";
        boolean condition = !StringUtils.isEmpty(dateRange) && !"null".equals(dateRange);
        if (condition) {
            startTime = dateRange.split(",")[0];
            endTime = dateRange.split(",")[1];
        } else {
            return ApiResponse.ofSuccess("参数错误");
        }
        List<BillTotalResponse> list = billService.billIncome(startTime, endTime);

        Map<String, BillTotalResponse> map = new HashMap<>(list.size());
        list.forEach(v -> map.put(v.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), v));
        LocalDate end = LocalDate.parse(endTime);
        LocalDate start = LocalDate.parse(startTime);
        List<String> betweenDate = getBetweenDate(start, end);
        List<BillTotalResponse> ret = new ArrayList<>();
        betweenDate.forEach(v -> {
            ret.add(map.getOrDefault(v, BillTotalResponse.builder().number(new BigDecimal(0)).date(LocalDate.parse(v)).build()));
        });
        return ApiResponse.ofSuccess(ret);
    }

    @GetMapping("/bill/outlay")
    public ApiResponse billOutlay(String dateRange) {
        String startTime = "", endTime = "";
        boolean condition = !StringUtils.isEmpty(dateRange) && !"null".equals(dateRange);
        if (condition) {
            startTime = dateRange.split(",")[0];
            endTime = dateRange.split(",")[1];
        } else {
            return ApiResponse.ofError("参数错误");
        }
        List<BillTotalResponse> list = billService.billOutlay(startTime, endTime);

        Map<String, BillTotalResponse> map = new HashMap<>(list.size());
        list.forEach(v -> map.put(v.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), v));
        LocalDate end = LocalDate.parse(endTime);
        LocalDate start = LocalDate.parse(startTime);
        List<String> betweenDate = getBetweenDate(start, end);
        List<BillTotalResponse> ret = new ArrayList<>();
        betweenDate.forEach(v -> {
            ret.add(map.getOrDefault(v, BillTotalResponse.builder().number(new BigDecimal(0)).date(LocalDate.parse(v)).build()));
        });
        return ApiResponse.ofSuccess(ret);
    }


    private static List<String> getBetweenDate(LocalDate start, LocalDate end) {
        long between = ChronoUnit.DAYS.between(start, end);
        if (between < 1) {
            return Stream.of(start.toString(), end.toString()).collect(Collectors.toList());
        }

        return Stream.iterate(start, e -> e.plusDays(1))
                .limit(between + 1)
                .map(LocalDate::toString)
                .collect(Collectors.toList());
    }
}
