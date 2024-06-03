package cn.imhtb.live.interceptor;

import cn.imhtb.live.annotation.NeedToken;
import cn.imhtb.live.exception.UnAuthException;
import cn.imhtb.live.holder.UserHolder;
import cn.imhtb.live.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.NonNull;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author pinteh
 */
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        String token = request.getHeader(JwtUtil.getHeaderKey());
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 需要验证的接口
        if (method.isAnnotationPresent(NeedToken.class)) {
            NeedToken needToken = method.getAnnotation(NeedToken.class);
            if (needToken.required()) {
                Claims claims = JwtUtil.verifyJwt(token);
                if (claims == null) {
                    throw new UnAuthException("认证失败，请重新登录");
                }
                Integer userId = Integer.valueOf(claims.getId());
                UserHolder.setUserId(userId);
            }
        }
        return true;
    }


    @Override
    public void afterCompletion(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, Exception ex) throws Exception {
        UserHolder.remove();
    }
    
}
