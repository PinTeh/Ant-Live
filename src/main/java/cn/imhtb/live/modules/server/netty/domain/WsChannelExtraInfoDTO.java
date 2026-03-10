package cn.imhtb.live.modules.server.netty.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author pinteh
 * @date 2023/6/4
 */
@Data
@Accessors(chain = true)
public class WsChannelExtraInfoDTO {

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 所在的房间列表
     */
    private Set<Integer> roomIds;

    public WsChannelExtraInfoDTO addRoomId(Integer roomId){
        if (Objects.isNull(this.roomIds)){
            this.roomIds = new HashSet<>();
        }
        this.roomIds.add(roomId);
        return this;
    }

    public static WsChannelExtraInfoDTO init(){
        WsChannelExtraInfoDTO wsChannelExtraInfo = new WsChannelExtraInfoDTO();
        wsChannelExtraInfo.setRoomIds(new HashSet<>());
        return wsChannelExtraInfo;
    }
}
