package cn.imhtb.live.common.interceptor;

import cn.imhtb.live.common.holder.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author pinteh
 * @date 2025/4/25
 */
@Slf4j
public class CollectInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        Object attribute = request.getAttribute(TokenInterceptor.ATTRIBUTE_UID);
        Integer userId = (Integer) attribute;
        UserHolder.setUserId(userId);
        return true;
    }

    @Override
    public void afterCompletion(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, Exception ex) throws Exception {
        UserHolder.remove();
    }

}
