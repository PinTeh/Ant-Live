package cn.imhtb.antlive.controller;

import cn.imhtb.antlive.annotation.NeedToken;
import cn.imhtb.antlive.common.ApiResponse;
import cn.imhtb.antlive.entity.User;
import cn.imhtb.antlive.service.ITokenService;
import cn.imhtb.antlive.service.IUserService;
import cn.imhtb.antlive.utils.*;
import cn.imhtb.antlive.vo.FrontMenuItem;
import cn.imhtb.antlive.vo.request.LoginRequest;
import cn.imhtb.antlive.vo.request.UserInfoUpdateRequest;
import cn.imhtb.antlive.vo.response.LoginResponse;
import cn.imhtb.antlive.vo.request.RegisterRequest;
import com.alibaba.fastjson.JSON;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author PinTeh
 */
@RestController
public class UserController {

    private final IUserService userService;

    private final RedisUtils redisUtils;

    private final SmsUtils smsUtils;

    private final MailUtils mailUtils;

    private final ModelMapper modelMapper;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private final ITokenService tokenService;

    public UserController(IUserService userService, RedisUtils redisUtils, SmsUtils smsUtils, MailUtils mailUtils, ModelMapper modelMapper, ITokenService tokenService) {
        this.userService = userService;
        this.redisUtils = redisUtils;
        this.smsUtils = smsUtils;
        this.mailUtils = mailUtils;
        this.modelMapper = modelMapper;
        this.tokenService = tokenService;
    }

    @RequestMapping("/login")
    public ApiResponse login(@RequestBody LoginRequest request){
        if (StringUtils.isEmpty(request.getAccount())||StringUtils.isEmpty(request.getPassword())){
            return ApiResponse.ofError("参数传递错误");
        }
        User user = userService.login(request.getAccount(), request.getPassword());
        if (user!=null){
            if (user.getDisabled()==1){
                return ApiResponse.ofError("账号已被封禁");
            }
            String jwt = JwtUtils.createToken(user);
            LoginResponse loginResponse = new LoginResponse(jwt, user);
//            if (user.getRoleId() != 0){
//                List<RolePower> rolePowers = rolePowerService.list();
//                List<FrontMenuItem> frontMenuItems = rolePowers.stream().map(v -> JSON.parseObject(v.getContent(), FrontMenuItem.class)).collect(Collectors.toList());
//                loginResponse.setMenu(frontMenuItems);
//            }
            return ApiResponse.ofSuccess(loginResponse);
        }
        return ApiResponse.ofError("账号或密码错误");
    }

    @PostMapping("/register")
    public ApiResponse register(@RequestBody RegisterRequest request){
        String account = request.getAccount();
        if (StringUtils.isEmpty(account)||StringUtils.isEmpty(request.getPassword())){
            return ApiResponse.ofError("参数传递错误");
        }

       return userService.register(request);
    }

    @PostMapping("/code")
    public ApiResponse smsEmailCode(String account){
        int code  = CommonUtils.getRandomCode();
        if (account.contains("@")){
            redisUtils.set("email:"+account,code,Long.valueOf("2"), TimeUnit.MINUTES);
            mailUtils.sendSimpleMessage(account,"直播注册验证码","您的动态验证码为："+code+"，有效时间为2分钟，如非本人操作，请忽略本短信！");
            return ApiResponse.ofSuccess();
        }else {
            redisUtils.set("mobile:"+account,code,Long.valueOf("2"), TimeUnit.MINUTES);
            ArrayList<String> params = new ArrayList<>();
            params.add(String.valueOf(code));
            smsUtils.txSmsSend(account,params);
            return ApiResponse.ofSuccess();
        }
    }

    @NeedToken
    @GetMapping("/getSecurityInfo")
    public ApiResponse getSecurityInfo(){
        Integer userId = tokenService.getUserId();
        return ApiResponse.ofSuccess(userService.getSecurityInfo(userId));
    }

    @PostMapping("/user/info/update")
    public ApiResponse updateUserInfo(@RequestBody UserInfoUpdateRequest request){
        Integer userId = tokenService.getUserId();
        User user = modelMapper.map(request, User.class);
        user.setId(userId);
        userService.updateById(user);
        return ApiResponse.ofSuccess("update success");
    }

    @NeedToken
    @GetMapping("/getUserInfo")
    public ApiResponse getUserInfo(){
        Integer userId = tokenService.getUserId();
        return ApiResponse.ofSuccess(userService.getById(userId));
    }

    @PostMapping("/bind")
    public ApiResponse bindEmail(String account,String code){
        String redisCode;
        Integer userId = tokenService.getUserId();
        User user = new User();
        user.setId(userId);
        if (account.contains("@")){
             redisCode = redisUtils.get("email:" + account).toString();
            if (!code.equals(redisCode)){
                return ApiResponse.ofError("验证码错误");
            }
            user.setEmail(account);
        }else {
            redisCode = redisUtils.get("mobile:" + account).toString();
            if (!code.equals(redisCode)){
                return ApiResponse.ofError("验证码错误");
            }
            user.setMobile(account);
        }
        return ApiResponse.ofSuccess(userService.updateById(user));
    }

}
