package cn.imhtb.live.service;


import com.tencentcloudapi.live.v20180801.models.DescribeLiveForbidStreamListResponse;
import com.tencentcloudapi.live.v20180801.models.DescribeLiveSnapshotTemplatesResponse;
import com.tencentcloudapi.live.v20180801.models.ModifyLiveSnapshotTemplateRequest;

/**
 * @author PinTeh
 * @date 2020/4/14
 */
public interface ITencentLiveService {

    boolean ban(Integer rid, String resumeTime, String reason);

    boolean resume(Integer rid);

    DescribeLiveForbidStreamListResponse banList(Integer page, Integer limit);

    DescribeLiveSnapshotTemplatesResponse snapshotTemplatesList();

    void snapshotTemplatesUpdate(ModifyLiveSnapshotTemplateRequest request);
}
