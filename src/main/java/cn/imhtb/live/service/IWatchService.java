package cn.imhtb.live.service;

import cn.imhtb.live.common.PageData;
import cn.imhtb.live.pojo.database.Watch;
import cn.imhtb.live.pojo.vo.response.WatchResponse;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author PinTeh
 * @date 2020/3/18
 */
public interface IWatchService extends IService<Watch> {

    /**
     * 获取用户关注列表
     *
     * @param userId 用户ID
     * @param type 类型
     * @param limit 每页数量
     * @param page 页码
     * @return 分页数据
     */
    PageData<WatchResponse> listWatches(Integer userId, Integer type, Integer limit, Integer page);

    /**
     * 判断用户是否关注了指定房间
     *
     * @param userId 用户ID
     * @param roomId 房间ID
     * @return 如果用户关注了指定房间，则返回true，否则返回false
     */
    Boolean follow(Integer userId, Integer roomId);

    /**
     * 取消关注
     *
     * @param userId 用户ID
     * @param roomId 房间ID
     * @return 是否取消关注成功
     */
    Boolean unFollow(Integer userId, Integer roomId);

    /**
     * 清除指定用户的浏览历史记录
     *
     * @param userId 用户ID
     * @return 是否成功清除历史记录，true表示成功，false表示失败
     */
    Boolean clearHistory(Integer userId);

    /**
     * 保存用户在指定房间内的历史记录
     *
     * @param userId 用户ID
     * @param roomId 房间ID
     * @return 是否保存成功
     */
    Boolean saveHistory(Integer userId, Integer roomId);

}
