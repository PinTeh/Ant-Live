package cn.imhtb.antlive.service;

import cn.imhtb.antlive.common.ApiResponse;
import cn.imhtb.antlive.entity.User;
import cn.imhtb.antlive.vo.request.RegisterRequest;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IUserService extends IService<User> {

    User login(String account, String password);

    boolean[] getSecurityInfo(Integer userId);

    void updateStatusByIds(Integer[] ids,Integer status);

    /**
     * 注册
     *
     * @param request 注册请求
     * @return {@link ApiResponse}
     */
    ApiResponse register(RegisterRequest request);

    Integer countToday();

}
