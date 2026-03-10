package cn.imhtb.antlive.filter;

import cn.imhtb.antlive.common.JwtConstants;
import cn.imhtb.antlive.service.impl.UserDetailsServiceImpl;
import cn.imhtb.antlive.utils.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author PinTeh
 * @date 2020/5/7
 * 过滤器处理所有HTTP请求，并检查是否存在带有正确令牌的Authorization标头。例如，如果令牌未过期或签名密钥正确。
 */
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String token = request.getHeader(JwtConstants.TOKEN_HEADER);
        if (token == null || !token.startsWith(JwtConstants.TOKEN_PREFIX)) {
            SecurityContextHolder.clearContext();
        } else {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

    /**
     * 获取用户认证信息 Authentication
     */
    private UsernamePasswordAuthenticationToken getAuthentication(String authorization) {
        try {
            String username = JwtUtils.getUsernameByToken(authorization);
            if (!StringUtils.isEmpty(username)) {
                // 这里我们是又从数据库拿了一遍,避免用户的角色信息有变
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
                return userDetails.isEnabled() ? usernamePasswordAuthenticationToken : null;
            }
        } catch (ExpiredJwtException | MalformedJwtException | IllegalArgumentException exception) {
            logger.warn("Request to parse JWT with invalid signature . Detail : " + exception.getMessage());
        }
        return null;
    }
}
