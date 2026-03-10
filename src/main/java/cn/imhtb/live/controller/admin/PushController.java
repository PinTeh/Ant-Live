package cn.imhtb.live.controller.admin;

import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.pojo.database.SysPush;
import cn.imhtb.live.pojo.database.SysPushLog;
import cn.imhtb.live.pojo.vo.request.SystemPushRequest;
import cn.imhtb.live.service.ISysPushLogService;
import cn.imhtb.live.service.ISysPushService;
import cn.imhtb.live.common.utils.JwtUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author PinTeh
 * @date 2020/5/8
 * 推送消息控制器
 */
@RestController
@RequestMapping("/admin/push")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PushController {

    private final ISysPushService systemPushService;
    private final ISysPushLogService sysPushLogService;

    @GetMapping("/log")
    public ApiResponse log(@RequestParam Integer sysPushId) {
        List<SysPushLog> list = sysPushLogService.list(new QueryWrapper<SysPushLog>().eq("sys_push_id", sysPushId).orderByDesc("id").last("limit 0,10"));
        return ApiResponse.ofSuccess(list);
    }

    @GetMapping("/info")
    public ApiResponse info(HttpServletRequest request) {
        Integer uid = JwtUtil.getId(request);
        return ApiResponse.ofSuccess(systemPushService.getOne(new QueryWrapper<SysPush>().eq("user_id", uid)));
    }

    @PostMapping("/update")
    public ApiResponse update(HttpServletRequest request, @RequestBody SystemPushRequest systemPushRequest) {
        Integer uid = JwtUtil.getId(request);
        SysPush update = new SysPush();
        BeanUtils.copyProperties(systemPushRequest, update);
        update.setUserId(uid);
        systemPushService.saveOrUpdate(update);
        return ApiResponse.ofSuccess();
    }
}
