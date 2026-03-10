package cn.imhtb.live.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 打赏类型枚举
 *
 * @author pinteh
 * @date 2023/02/16
 */
@Getter
@AllArgsConstructor
public enum PresentRewardTypeEnum {

    /**
     * 打赏类型
     */
    LIVE(0, "LIVE"),
    VIDEO(1, "VIDEO");

    private final Integer code;
    private final String desc;

}
