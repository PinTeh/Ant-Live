package cn.imhtb.live.exception.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author pinteh
 */

@Getter
@AllArgsConstructor
public enum CommonErrorCode implements IErrorCode {
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
    UN_AUTH_ERROR(401, "Un auth error")
    ;


    private final int code;

    private final String msg;

}
