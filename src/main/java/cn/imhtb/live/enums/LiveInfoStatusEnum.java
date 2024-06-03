package cn.imhtb.live.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 直播信息状态枚举
 *
 * @author pinteh
 * @date 2023/02/16
 */
@Getter
@AllArgsConstructor
public enum LiveInfoStatusEnum {
    /**
     * 直播信息状态描述
     */
    NO(0, "未完成"),
    YES(1, "完成"),
    ;

    private final int code;
    private final String desc;

}
