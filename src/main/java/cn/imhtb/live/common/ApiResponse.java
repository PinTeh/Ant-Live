package cn.imhtb.live.common;

import cn.imhtb.live.common.exception.base.CommonErrorCode;
import cn.imhtb.live.common.exception.base.IErrorCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author pinteh
 */
@Setter
@Getter
@ApiModel("响应对象")
public class ApiResponse<T> implements Serializable {

    @ApiModelProperty("状态码")
    private int code;

    @ApiModelProperty("响应消息")
    private String msg;

    @ApiModelProperty("响应数据")
    private T data;

    private ApiResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private ApiResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return this.code == CommonErrorCode.SUCCESS.getCode();
    }

    public  static <T> ApiResponse<T> ofSuccess() {
        return new ApiResponse<>(CommonErrorCode.SUCCESS.getCode(), CommonErrorCode.SUCCESS.getMsg());
    }

    public static <T> ApiResponse<T> ofSuccess(T o) {
        return new ApiResponse<>(CommonErrorCode.SUCCESS.getCode(), CommonErrorCode.SUCCESS.getMsg(), o);
    }

    public static <T> ApiResponse<T> ofSuccess(int code, String msg, T o) {
        return new ApiResponse<>(code, msg, o);
    }

    public static <T> ApiResponse<T> ofError(int code, String msg, T o) {
        return new ApiResponse<>(code, msg, o);
    }

    public static <T> ApiResponse<T> ofMessageSuccess(String msg) {
        return new ApiResponse<>(CommonErrorCode.SUCCESS.getCode(), msg);
    }

    public static <T> ApiResponse<T> ofSuccess(String msg, T o) {
        return new ApiResponse<>(CommonErrorCode.SUCCESS.getCode(), msg, o);
    }

    public static <T> ApiResponse<T> ofError() {
        return new ApiResponse<>(CommonErrorCode.SERVICE_ERROR.getCode(), CommonErrorCode.SERVICE_ERROR.getMsg());
    }

    public static <T> ApiResponse<T> ofError(int code, String msg) {
        return new ApiResponse<>(code, msg);
    }

    public static <T> ApiResponse<T> ofError(String msg) {
        return new ApiResponse<>(CommonErrorCode.SERVICE_ERROR.getCode(), msg);
    }

    public static <T> ApiResponse<T> ofError(T o) {
        return new ApiResponse<>(CommonErrorCode.SERVICE_ERROR.getCode(), CommonErrorCode.SERVICE_ERROR.getMsg(), o);
    }

    public static <T> ApiResponse<T> ofError(String msg, T o) {
        return new ApiResponse<>(CommonErrorCode.SERVICE_ERROR.getCode(), msg, o);
    }

    public static <T> ApiResponse<T> ofError(IErrorCode errorCode) {
        return new ApiResponse<>(errorCode.getCode(), errorCode.getMsg());
    }

}
