package cn.imhtb.live.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 直播间状态枚举
 *
 * @author pinteh
 * @date 2023/02/16
 */
@Getter
@AllArgsConstructor
public enum LiveRoomStatusEnum {

    /**
     * 直播状态描述
     */
    UN_AUTH(-1, "未认证"),
    STOP(0, "未开播"),
    LIVING(1, "正在直播中"),
    BANNING(2, "已被封禁");

    private final int code;
    private final String desc;

}
