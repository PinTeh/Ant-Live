package cn.imhtb.antlive.handler;

import cn.imhtb.antlive.common.ApiResponse;
import cn.imhtb.antlive.common.ResponseCode;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

/**
 * @author PinTeh
 */
@RestControllerAdvice
public class AntLiveExceptionHandler {

    @ExceptionHandler(value = UnAuthException.class)
    public ApiResponse handleUnAuthException(UnAuthException ex){
        return ApiResponse.ofError(ResponseCode.UN_AUTH_ERROR.getCode(),ex.getMessage());
    }

    /*
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApiResponse runtimeExceptionHandler(RuntimeException runtimeException) {
        //logger.error(runtimeException.getMessage());
        return ApiResponse.ofError(ResponseCode.UN_KNOW_ERROR.getCode(),"系统运行异常");
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApiResponse nullPointerExceptionHandler(NullPointerException ex) {
        //logger.error(ex.getMessage());
        return ApiResponse.ofError(ResponseCode.UN_KNOW_ERROR.getCode(),"系统空指针异常");
    }

    //400错误
    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResponse requestNotReadable(HttpMessageNotReadableException ex){
        //logger.error(ex.getMessage());
        return ApiResponse.ofError(ResponseCode.UN_KNOW_ERROR.getCode(),"参数格式错误(缺少分隔符或结束标签)");
    }

    //400错误
    @ExceptionHandler({TypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResponse requestTypeMismatch(TypeMismatchException ex){
        //logger.error(ex.getMessage());
        return ApiResponse.ofError(ResponseCode.UN_KNOW_ERROR.getCode(),"参数类型不匹配");
    }

    //400错误
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResponse requestMissingServletRequest(MissingServletRequestParameterException ex){
        //logger.error(ex.getMessage());
        return ApiResponse.ofError(ResponseCode.UN_KNOW_ERROR.getCode(),"缺少请求参数");
    }

    //405错误
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public ApiResponse request405(){
        return ApiResponse.ofError(ResponseCode.UN_KNOW_ERROR.getCode(),"不支持该请求方式");
    }

    */
}
