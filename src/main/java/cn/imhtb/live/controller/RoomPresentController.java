package cn.imhtb.live.controller;

import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.pojo.database.PresentReward;
import cn.imhtb.live.service.IPresentRewardService;
import cn.imhtb.live.utils.JwtUtil;
import cn.imhtb.live.pojo.vo.response.PresentRewardResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author PinTeh
 * @date 2020/4/8
 */
@RestController
@RequestMapping("/room/present")
public class RoomPresentController {

    //private String column = "id,from_id,to_id,room_id,present_id,number,unit_price,total_price,create_time,update_time,present_name";

    private final IPresentRewardService presentRewardService;

    public RoomPresentController(IPresentRewardService presentRewardService) {
        this.presentRewardService = presentRewardService;
    }

    @GetMapping("/list")
    public ApiResponse list(HttpServletRequest request
            , @RequestParam(required = false, defaultValue = "10") Integer limit
            , @RequestParam(required = false, defaultValue = "1") Integer page
            , @RequestParam(required = false) String dateRange, @RequestParam(required = false) Integer t) {
        Integer uid = JwtUtil.getId(request);
        String maxTime = "", minTime = "";
        boolean condition = !StringUtils.isEmpty(dateRange) && !"null".equals(dateRange);
        if (condition) {
            maxTime = dateRange.split(",")[1];
            minTime = dateRange.split(",")[0];
        }
        QueryWrapper<PresentReward> wrapper = new QueryWrapper<PresentReward>()
                .eq("to_id", uid)
                .eq(t != null, "type", t)
                .le(condition, "create_time", maxTime)
                .ge(condition, "create_time", minTime)
                .orderByDesc("id");
        Page<PresentReward> roomPresentPage = presentRewardService.page(new Page<>(page, limit), wrapper);
        return ApiResponse.ofSuccess(roomPresentPage);
    }

    private List<PresentRewardResponse> packagePresentName(List<PresentReward> list) {
        return null;
    }
}
