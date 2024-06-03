package cn.imhtb.live.modules.server.netty.web;

import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.modules.server.netty.domain.req.ChatMsgReq;
import cn.imhtb.live.modules.server.netty.service.IRoomChatService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author pinteh
 * @date 2023/6/13
 */
@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ChatController {

    private final IRoomChatService roomChatService;

    @ApiOperation("发送消息")
    @PostMapping("/sendMsg")
    public void sendChatMsg(@RequestBody @Valid ChatMsgReq chatMsgReq) {
        roomChatService.sendChatMsg(chatMsgReq);
    }

    @ApiOperation("获取房间人气")
    @GetMapping("/getPopularity")
    public ApiResponse<Long> getPopularity(@RequestParam Integer roomId) {
        return ApiResponse.ofSuccess(roomChatService.getRoomUserSize(roomId));
    }

}
