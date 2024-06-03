package cn.imhtb.live.service;

import cn.imhtb.live.pojo.Room;
import cn.imhtb.live.pojo.vo.RoomExtraInfo;
import cn.imhtb.live.pojo.vo.request.RoomInfoSaveRequest;
import cn.imhtb.live.pojo.vo.response.RoomResponse;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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
     * @param cid 直播分类
     * @return {@link List}<{@link RoomResponse}>
     */
    List<RoomResponse> getLivingRooms(Integer cid);

    /**
     * 获取房间信息
     *
     * @param rid 房间号
     * @return {@link RoomResponse}
     */
    RoomResponse getRoomInfo(Integer rid);

    /**
     * 得到额外信息
     *
     * @param rid 房间号码
     * @return {@link RoomExtraInfo}
     */
    RoomExtraInfo getExtraInfo(Integer rid);

}
