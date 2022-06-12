package cn.imhtb.antlive.filter;

import cn.imhtb.antlive.common.ApiResponse;
import cn.imhtb.antlive.entity.JwtUser;
import cn.imhtb.antlive.utils.JwtUtils;
import cn.imhtb.antlive.vo.request.LoginRequest;
import cn.imhtb.antlive.vo.response.JwtLoginResponse;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author PinTeh
 * @date 2020/5/7
 */
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    //private ThreadLocal<Boolean> rememberMe = new ThreadLocal<>();
    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        // 设置登录请求的 URL
        super.setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // 从输入流中获取到登录的信息
            LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
            //rememberMe.set(loginRequest.getRememberMe());
            log.info("test login request, {}", loginRequest.toString());
            // 这部分和attemptAuthentication方法中的源码是一样的，
            // 只不过由于这个方法源码的是把用户名和密码这些参数的名字是死的，所以我们重写了一下
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), loginRequest.getPassword());
            return authenticationManager.authenticate(authRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



    /**
     * 如果验证成功，就生成token并返回
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authentication) throws IOException {

        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
        List<String> roles = jwtUser.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        // 创建 Token
        String token = JwtUtils.createTokenByParams(jwtUser.getId(),"",jwtUser.getUsername());
        // Http Response Header 中返回 Token
        // response.setHeader(JwtUtils.getHeaderKey(), token);
        JwtLoginResponse loginResponse = new JwtLoginResponse(token, jwtUser);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(ApiResponse.ofSuccess(loginResponse)));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException {
        //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authenticationException.getMessage());
        ApiResponse apiResponse  = ApiResponse.ofError(1,"用户名或密码错误");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(apiResponse));
    }

}
