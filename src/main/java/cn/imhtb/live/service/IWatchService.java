package cn.imhtb.live.service;

import cn.imhtb.live.common.PageData;
import cn.imhtb.live.pojo.database.Watch;
import cn.imhtb.live.pojo.vo.request.RelationSaveRequest;
import cn.imhtb.live.pojo.vo.response.WatchResponse;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author PinTeh
 * @date 2020/3/18
 */
public interface IWatchService extends IService<Watch> {

    /**
     * 保存关系
     *
     * @param request 请求
     * @return {@link Boolean}
     */
    Boolean saveRelation(RelationSaveRequest request);

    /**
     * 列表
     *
     * @param type  类型
     * @param limit 限制
     * @param page  页面
     * @return {@link PageData}<{@link WatchResponse}>
     */
    PageData<WatchResponse> listWatches(Integer type, Integer limit, Integer page);

    /**
     * 清除历史
     *
     * @return {@link Boolean}
     */
    Boolean clearHistory();

}
