package cn.imhtb.live.filter;

import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.pojo.AntLiveUserBo;
import cn.imhtb.live.pojo.vo.UserInfoVo;
import cn.imhtb.live.pojo.vo.request.LoginRequest;
import cn.imhtb.live.pojo.vo.response.JwtLoginResponse;
import cn.imhtb.live.utils.JwtUtil;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author PinTeh
 * @date 2020/5/7
 */
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

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

        AntLiveUserBo antLiveUserBo = (AntLiveUserBo) authentication.getPrincipal();
        // 创建 Token
        String token = JwtUtil.createTokenByParams(antLiveUserBo.getId(), antLiveUserBo.getNickname(), antLiveUserBo.getUsername());
        // Http Response Body 中返回 Token
        UserInfoVo userInfoVo = new UserInfoVo();
        userInfoVo.setUserId(antLiveUserBo.getId());
        userInfoVo.setUsername(antLiveUserBo.getUsername());
        userInfoVo.setNickName(antLiveUserBo.getNickname());
        userInfoVo.setAvatar(antLiveUserBo.getAvatar());
        userInfoVo.setSignature(antLiveUserBo.getSignature());
        userInfoVo.setRoleIds(antLiveUserBo.getRoleIds());
        userInfoVo.setBalance(antLiveUserBo.getBalance());
        JwtLoginResponse loginResponse = new JwtLoginResponse(token, userInfoVo);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(ApiResponse.ofSuccess(loginResponse)));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException {
        ApiResponse<?> apiResponse = ApiResponse.ofError(1, "用户名或密码错误");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(apiResponse));
    }

}
