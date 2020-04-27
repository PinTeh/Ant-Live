package cn.imhtb.antlive.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiResponse {
    
    private int code;
    
    private String msg;
    
    private Object data;

    private ApiResponse(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    private ApiResponse(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    @JsonIgnore
    public boolean isSuccess(){
        return this.code == ResponseCode.SUCCESS.getCode();
    }

    public static ApiResponse ofSuccess(){
        return new ApiResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getDesc());
    }

    public static ApiResponse ofSuccess(Object o){
        return new ApiResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getDesc(),o);
    }

    public static ApiResponse ofSuccess(int code,String msg,Object o){
        return new ApiResponse(code,msg,o);
    }

    public static ApiResponse ofError(int code,String msg,Object o){
        return new ApiResponse(code,msg,o);
    }

    public static ApiResponse ofSuccess(String msg){
        return new ApiResponse(ResponseCode.SUCCESS.getCode(),msg);
    }

    public static ApiResponse ofSuccess(String msg,Object o){
        return new ApiResponse(ResponseCode.SUCCESS.getCode(),msg,o);
    }

    public static ApiResponse ofError(){
        return new ApiResponse(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
    }

    public static ApiResponse ofError(int code,String msg){
        return new ApiResponse(code,msg);
    }

    public static ApiResponse ofError(String msg){
        return new ApiResponse(ResponseCode.ERROR.getCode(),msg);
    }

    public static ApiResponse ofError(Object o){
        return new ApiResponse(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc(),o);
    }

    public static ApiResponse ofError(String msg,Object o){
        return new ApiResponse(ResponseCode.ERROR.getCode(),msg,o);
    }
}
