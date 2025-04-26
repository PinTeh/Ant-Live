package cn.imhtb.live.modules.user.service;

import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.pojo.database.User;
import cn.imhtb.live.modules.user.model.req.UserExtraReq;
import cn.imhtb.live.modules.user.model.req.UserRegisterReq;
import cn.imhtb.live.modules.user.model.req.UserInfoUpdateReq;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author pinteh
 */
public interface IUserService extends IService<User> {

    /**
     * 更新用户状态
     *
     * @param ids    用户id集合
     * @param status 状态码
     */
    void updateStatusByIds(Integer[] ids, Integer status);

    /**
     * 注册
     *
     * @param request 注册请求
     * @return {@link ApiResponse}
     */
    boolean register(UserRegisterReq request);

    Integer countToday();

    /**
     * 绑定额外信息
     *
     * @param userExtraReq 额外信息绑定请求参数
     * @return boolean
     */
    boolean bindUserExtra(UserExtraReq userExtraReq);

    /**
     * 更新用户信息
     *
     * @param request 请求
     * @return boolean
     */
    boolean updateUserInfo(UserInfoUpdateReq request);

    /**
     * 获取用户信息
     *
     * @return {@link User}
     */
    User getUserInfo();

    /**
     * 更新头像
     *
     * @param avatarUrl 头像url
     */
    void updateAvatar(String avatarUrl);

}
