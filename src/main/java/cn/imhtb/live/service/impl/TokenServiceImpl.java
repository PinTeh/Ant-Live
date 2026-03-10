package cn.imhtb.live.service.impl;

import cn.imhtb.live.common.exception.UnAuthException;
import cn.imhtb.live.service.ITokenService;
import cn.imhtb.live.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author pinteh
 */
@Service
public class TokenServiceImpl implements ITokenService {

    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
    }

    @Override
    public Claims get() {
        HttpServletRequest request = this.getRequest();
        String token = request.getHeader(JwtUtil.getHeaderKey());
        if (token != null) {
            return JwtUtil.verify(token);
        }
        throw new UnAuthException("无法获取到token，请重新登录后尝试");
    }

    @Override
    public Integer getUserId() {
        Claims claims = get();
        if (claims == null){
            throw new UnAuthException("无法获取到token，请重新登录后尝试");
        }
        return Integer.valueOf(claims.getId());
    }

}
