package cn.imhtb.antlive.controller;

import cn.imhtb.antlive.vo.lal.PubVo;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@Slf4j
@RestController
public class LalController {

    @PostMapping("/on_pub_start")
    public void pubStart(@RequestBody PubVo pubVo){
        log.info("on_pub_start接收到推送回调: {}", JSON.toJSONString(pubVo));
    }

    @PostMapping("/on_update")
    public void pubStart(@RequestBody String pubVo){
        log.info("on_update接收到推送回调: {}", JSON.toJSONString(pubVo));
    }

}
