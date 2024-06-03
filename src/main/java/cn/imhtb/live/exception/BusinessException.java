package cn.imhtb.live.exception;

import cn.imhtb.live.exception.base.CommonErrorCode;
import cn.imhtb.live.exception.base.IErrorCode;

/**
 * @author pinteh
 * @date 2023/2/28
 */
public class BusinessException extends RuntimeException implements IErrorCode {

    private final int code;

    private final String msg;


    public BusinessException(IErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }

    public BusinessException(IErrorCode errorCode, String msg) {
        this.code = errorCode.getCode();
        this.msg = msg;
    }

    public BusinessException(String msg){
        this.code = CommonErrorCode.SERVICE_ERROR.getCode();
        this.msg = msg;
    }

    public BusinessException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BusinessException(String msg, Throwable cause) {
        super(msg, cause);
        this.code = CommonErrorCode.SERVICE_ERROR.getCode();
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
