package cn.imhtb.live.exception.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author pinteh
 */

@Getter
@AllArgsConstructor
public enum UserErrorCode implements IErrorCode {
    /**
     * 成功
     */
    SUCCESS(0, "Success"),
    /**
     * 服务错误
     */
    SERVICE_ERROR(1, "Service error"),
    /**
     * 未认证错误
     */
    ERR_USERNAME_REPEAT(10001, "当前用户名已存在")
    ;


    private final int code;

    private final String msg;

}
