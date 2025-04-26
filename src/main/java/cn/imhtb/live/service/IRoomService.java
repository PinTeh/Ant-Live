package cn.imhtb.live.service;

import cn.imhtb.live.common.PageData;
import cn.imhtb.live.modules.live.vo.RoomRespVo;
import cn.imhtb.live.pojo.database.Room;
import cn.imhtb.live.pojo.vo.RoomExtraInfoResp;
import cn.imhtb.live.pojo.vo.request.RoomInfoSaveRequest;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author pinteh
 */
public interface IRoomService extends IService<Room> {

    /**
     * 更新封面
     *
     * @param coverUrl 封面url
     */
    void updateCover(String coverUrl);

    /**
     * 保存信息
     *
     * @param request 请求
     * @return boolean
     */
    boolean saveInfo(RoomInfoSaveRequest request);

    /**
     * 获取正在直播的房间
     *
     * @param cid      直播分类
     * @param pageNo   页码
     * @param pageSize  页面大小
     * @return {@link PageData}<{@link RoomRespVo}>
     */
    PageData<RoomRespVo> getLivingRooms(Integer cid, Integer pageNo, Integer pageSize);

    /**
     * 获取房间信息
     *
     * @param roomId 房间号
     * @return {@link RoomRespVo}
     */
    RoomRespVo getRoomInfo(Integer roomId);

    /**
     * 得到额外信息
     *
     * @param rid 房间号码
     * @return {@link RoomExtraInfoResp}
     */
    RoomExtraInfoResp getExtraInfo(Integer userId, Integer rid);

}
