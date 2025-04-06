package cn.imhtb.live.common.handler;

import cn.imhtb.live.common.ApiResponse;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用来解决匿名用户访问需要权限才能访问的资源时的异常
 *
 * @author PinTeh
 * @date 2020/5/7
 */
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * 当用户尝试访问需要权限才能的REST资源而不提供Token或者Token错误或者过期时，
     * 将调用此方法发送401响应以及错误信息
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
        ApiResponse<?> apiResponse = ApiResponse.ofError(401, "认证失败，请重新登录~");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(apiResponse));
    }

}