package cn.imhtb.live.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author pinteh
 * @date 2023/5/31
 */
@AllArgsConstructor
@Getter
public enum WatchTypeEnum {
    /**
     * 类型
     */
    HISTORY(0, "历史记录"),
    FOLLOW(1, "关注")
    ;
    private final int code;
    private final String desc;
}
