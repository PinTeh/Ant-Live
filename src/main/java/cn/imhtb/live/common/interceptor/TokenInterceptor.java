package cn.imhtb.live.common.interceptor;

import cn.hutool.http.ContentType;
import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.common.annotation.IgnoreToken;
import cn.imhtb.live.common.constants.JwtConstant;
import cn.imhtb.live.common.utils.JwtUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.slf4j.MDC;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author pinteh
 * @date 2025/4/25
 */
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

    public static final String ATTRIBUTE_UID = "userId";

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        String token = request.getHeader(JwtConstant.TOKEN_HEADER);
        Integer userId = JwtUtil.verifyGetUserId(token);
        if (userId != null){
            request.setAttribute(ATTRIBUTE_UID, userId);
        } else {
            // 判断是否是忽略token的接口
            boolean ignoreApi = isIgnoreApi(handler);
            // 判断是否为公共资源
            boolean isPublicResource = isPublicResource(request.getRequestURI());
            if (!ignoreApi && !isPublicResource){
                // 返回401
                response.setStatus(401);
                response.setContentType(ContentType.JSON.toString(Charsets.UTF_8));
                response.getWriter().write(JSON.toJSONString(ApiResponse.ofError("登录状态失效，请重新登录。")));
                return false;
            }
        }
        MDC.put(ATTRIBUTE_UID, String.valueOf(userId));
        return true;
    }

    private boolean isIgnoreApi(Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            IgnoreToken annotation = method.getAnnotation(IgnoreToken.class);
            return annotation != null;
        }
        return false;
    }

    private boolean isPublicResource(String uri){
        return uri.contains("/public");
    }

    @Override
    public void afterCompletion(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, Exception ex) throws Exception {
        MDC.remove("userId");
    }

}
