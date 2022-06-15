package cn.imhtb.antlive.handler;

import cn.imhtb.antlive.common.ApiResponse;
import cn.imhtb.antlive.common.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

/**
 * @author PinTeh
 */
@Slf4j
@RestControllerAdvice
public class AntLiveExceptionHandler {

    @ExceptionHandler(value = UnAuthException.class)
    public ApiResponse handleUnAuthException(UnAuthException e){
        log.error("auth exception", e);
        return ApiResponse.ofError(ResponseCode.UN_AUTH_ERROR.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public ApiResponse handleException(Exception e){
        log.error("service exception", e);
        return ApiResponse.ofError("Service error");
    }

}
