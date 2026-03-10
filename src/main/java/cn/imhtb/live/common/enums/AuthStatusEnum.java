package cn.imhtb.live.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 身份验证状态
 *
 * @author pinteh
 * @date 2023/02/16
 */
@Getter
@AllArgsConstructor
public enum AuthStatusEnum {

    /**
     * 认证描述
     */
    NO(0, "等待认证"),
    PASS(1, "认证通过"),
    REJECT(3, "认证失败"),
    AUTO_PASS(2, "智能审核通过");

    private final int code;
    private final String desc;

}
