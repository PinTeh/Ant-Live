package cn.imhtb.antlive.service.impl;

import cn.imhtb.antlive.handler.UnAuthException;
import cn.imhtb.antlive.service.ITokenService;
import cn.imhtb.antlive.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Service
public class TokenServiceImpl implements ITokenService {

    private HttpServletRequest getRequest(){
        return  ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
    }

    @Override
    public Claims get() {
        HttpServletRequest request = this.getRequest();
        String token = request.getHeader(JwtUtils.getHeaderKey());
        if (token!=null){
            return JwtUtils.verifyJWT(token);
        }
        throw new UnAuthException("无法获取到token，请重新登录后尝试");
    }

    @Override
    public void set() {

    }

    @Override
    public Integer getUserId() {
        Claims claims = get();
        return Integer.valueOf(claims.getId());
    }
}
