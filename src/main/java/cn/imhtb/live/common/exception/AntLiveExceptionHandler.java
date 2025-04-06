package cn.imhtb.live.common.exception;

import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.common.exception.base.CommonErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * 异常处理器
 *
 * @author PinTeh
 */
@Slf4j
@RestControllerAdvice
public class AntLiveExceptionHandler {

    @ExceptionHandler(value = BusinessException.class)
    public ApiResponse<?> handleBusinessException(BusinessException e) {
        log.error("catch business exception", e);
        return ApiResponse.ofError(e.getCode(), e.getMsg());
    }

    /**
     * 捕获认证异常
     *
     * @param e 认证异常
     */
    @ExceptionHandler(value = UnAuthException.class)
    public ApiResponse<?> handleUnAuthException(UnAuthException e) {
        log.error("catch authentication exception", e);
        return ApiResponse.ofError(CommonErrorCode.UN_AUTH_ERROR);
    }

    /**
     * 捕获服务异常
     *
     * @param e 服务异常
     */
    @ExceptionHandler(value = Exception.class)
    public ApiResponse<?> handleException(Exception e) {
        log.error("catch service exception", e);
        return ApiResponse.ofError(CommonErrorCode.SERVICE_ERROR);
    }

    /**
     * 方法参数
     *
     * @param exception ConstraintViolationException
     * @return BaseResponse
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<String> handleConstraintViolationException(ConstraintViolationException exception) {
        StringBuilder errorInfo = new StringBuilder();
        String errorMessage;
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        for (ConstraintViolation<?> item : violations) {
            errorInfo.append(item.getMessage()).append(",");
        }
        errorMessage = errorInfo.substring(0, errorInfo.toString().length() - 1);
        return ApiResponse.ofError(CommonErrorCode.SERVICE_ERROR.getCode(), errorMessage);
    }

    /**
     * 实体类
     *
     * @param exception MethodArgumentNotValidException
     * @return BaseResponse
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<String> handleMethodArgumentNotValidException(Exception exception) {
        BindingResult bindingResult = null;
        if (exception instanceof MethodArgumentNotValidException) {
            bindingResult = ((MethodArgumentNotValidException) exception).getBindingResult();
        } else if (exception instanceof BindException) {
            bindingResult = ((BindException) exception).getBindingResult();
        }
        StringBuilder errorInfo = new StringBuilder();
        String errorMessage = null;
        if (bindingResult != null) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errorInfo.append(fieldError.getDefaultMessage()).append(",");
            }
            errorMessage = errorInfo.substring(0, errorInfo.toString().length() - 1);
        }
        return ApiResponse.ofError(CommonErrorCode.SERVICE_ERROR.getCode(), errorMessage);
    }

}
