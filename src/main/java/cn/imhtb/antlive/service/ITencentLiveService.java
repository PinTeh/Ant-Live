package cn.imhtb.antlive.service;


import com.tencentcloudapi.live.v20180801.models.DescribeLiveForbidStreamListResponse;

import java.util.List;

/**
 * @author PinTeh
 * @date 2020/4/14
 */
public interface ITencentLiveService {

    boolean ban(Integer rid,String resumeTime) ;

    boolean resume(Integer rid) ;

    DescribeLiveForbidStreamListResponse banList(Integer page,Integer limit);
}
