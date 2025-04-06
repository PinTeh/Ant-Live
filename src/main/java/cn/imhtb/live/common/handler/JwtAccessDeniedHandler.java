package cn.imhtb.live.common.handler;

import cn.imhtb.live.common.ApiResponse;
import com.alibaba.fastjson.JSON;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用来解决认证过的用户访问无权限资源时的异常
 *
 * @author PinTeh
 * @date 2020/5/7
 */
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * 当用户尝试访问需要权限才能的REST资源而权限不足的时候，
     * 将调用此方法发送403响应以及错误信息
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        //accessDeniedException = new AccessDeniedException("Sorry you don not enough permissions to access it!");
        //response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());

        ApiResponse<?> apiResponse = ApiResponse.ofError(403, "权限不足~");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(apiResponse));
    }

}
