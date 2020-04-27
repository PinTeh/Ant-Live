package cn.imhtb.antlive.controller;

import cn.imhtb.antlive.common.ApiResponse;
import cn.imhtb.antlive.common.Constants;
import cn.imhtb.antlive.entity.Present;
import cn.imhtb.antlive.entity.Room;
import cn.imhtb.antlive.service.IPresentService;
import cn.imhtb.antlive.service.IRoomPresentService;
import cn.imhtb.antlive.service.IRoomService;
import cn.imhtb.antlive.utils.JwtUtils;
import cn.imhtb.antlive.vo.request.SendPresentRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author PinTeh
 */
@RestController
@RequestMapping("/present")
public class PresentController {

    private final IPresentService presentService;

    private final IRoomService roomService;

    private final IRoomPresentService roomPresentService;

    public PresentController(IPresentService presentService, IRoomService roomService, IRoomPresentService roomPresentService) {
        this.presentService = presentService;
        this.roomService = roomService;
        this.roomPresentService = roomPresentService;
    }

    @GetMapping()
    public ApiResponse list(){
        List<Present> list = presentService.list(new QueryWrapper<Present>().eq("disabled", Constants.DisabledStatus.YES.getCode()).orderByDesc("sort"));
        return ApiResponse.ofSuccess(list);
    }

    /**
     * give presents
     */
    @PostMapping("/send")
    public ApiResponse send(@RequestBody SendPresentRequest sendPresentRequest, HttpServletRequest request){
        String token = request.getHeader(JwtUtils.getHeaderKey());
        Integer uid = JwtUtils.getId(token);
        Room room = roomService.getById(sendPresentRequest.getRid());
        Present present = presentService.getById(sendPresentRequest.getPid());
        return roomPresentService.create(uid, present, room, sendPresentRequest.getNumber())?ApiResponse.ofSuccess():ApiResponse.ofError();
    }
}
