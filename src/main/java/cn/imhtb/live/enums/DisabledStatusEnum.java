package cn.imhtb.live.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 禁用状态枚举
 *
 * @author pinteh
 * @date 2023/02/16
 */
@Getter
@AllArgsConstructor
public enum DisabledStatusEnum {
    /**
     * 可用状态描述
     */
    YES(0, "可使用"),
    NO(1, "不可使用");

    private final int code;
    private final String desc;

}
