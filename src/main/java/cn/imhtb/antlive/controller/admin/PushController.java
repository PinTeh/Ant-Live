package cn.imhtb.antlive.controller.admin;

import cn.imhtb.antlive.common.ApiResponse;
import cn.imhtb.antlive.entity.database.SysPush;
import cn.imhtb.antlive.entity.database.SysPushLog;
import cn.imhtb.antlive.service.ISysPushLogService;
import cn.imhtb.antlive.service.ISysPushService;
import cn.imhtb.antlive.utils.JwtUtils;
import cn.imhtb.antlive.vo.request.SystemPushRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.modelmapper.ModelMapper;
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
public class PushController {

    private final ISysPushLogService sysPushLogService;

    private final ISysPushService systemPushService;

    private final ModelMapper modelMapper;

    public PushController(ISysPushService systemPushService, ModelMapper modelMapper, ISysPushLogService sysPushLogService) {
        this.systemPushService = systemPushService;
        this.modelMapper = modelMapper;
        this.sysPushLogService = sysPushLogService;
    }

    @GetMapping("/log")
    public ApiResponse log(@RequestParam Integer sysPushId){
        List<SysPushLog> list = sysPushLogService.list(new QueryWrapper<SysPushLog>().eq("sys_push_id",sysPushId).orderByDesc("id").last("limit 0,10"));
        return ApiResponse.ofSuccess(list);
    }

    @GetMapping("/info")
    public ApiResponse info(HttpServletRequest request){
        Integer uid = JwtUtils.getId(request);
        return ApiResponse.ofSuccess(systemPushService.getOne(new QueryWrapper<SysPush>().eq("user_id",uid)));
    }

    @PostMapping("/update")
    public ApiResponse update(HttpServletRequest request, @RequestBody SystemPushRequest systemPushRequest){
        Integer uid = JwtUtils.getId(request);
        SysPush update = modelMapper.map(systemPushRequest, SysPush.class);
        if (update.getId() == null){
            update.setUserId(uid);
            systemPushService.save(update);
        }else{
            systemPushService.updateById(update);
        }
        return ApiResponse.ofSuccess();
    }
}
