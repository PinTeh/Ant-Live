package cn.imhtb.live.controller;

import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.pojo.vo.lal.PubVO;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * lal直播
 *
 * @author pinteh
 */
@Slf4j
@RestController
public class LalController {

    @PostMapping("/lolo")
    public ApiResponse<?> lolo(@RequestBody Map<String, String> map){
        return ApiResponse.ofSuccess(map);
    }

    @PostMapping("/on_pub_start")
    public void pubStart(@RequestBody PubVO pubVo) {
        log.info("on_pub_start接收到推送回调: {}", JSON.toJSONString(pubVo));
    }

    @PostMapping("/on_update")
    public void pubStart(@RequestBody String pubVo) {
        log.info("on_update接收到推送回调: {}", JSON.toJSONString(pubVo));
    }

}
