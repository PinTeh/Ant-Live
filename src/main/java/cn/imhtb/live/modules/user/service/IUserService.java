package cn.imhtb.live.modules.user.service;

import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.pojo.User;
import cn.imhtb.live.pojo.vo.request.BindExtraInfoRequestVO;
import cn.imhtb.live.pojo.vo.request.RegisterRequest;
import cn.imhtb.live.pojo.vo.request.UserInfoUpdateRequest;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author pinteh
 */
public interface IUserService extends IService<User> {

    /**
     * 通过账户获取用户
     *
     * @param account 账户
     * @return {@link User}
     */
    User getByAccount(String account);

    User login(String account, String password);

    /**
     * 获取安全认证信息
     *
     * @return {@link boolean[]}
     */
    boolean[] getSecurityInfo();

    /**
     *
     * @param ids
     * @param status
     */
    void updateStatusByIds(Integer[] ids, Integer status);

    /**
     * 注册
     *
     * @param request 注册请求
     * @return {@link ApiResponse}
     */
    boolean register(RegisterRequest request);

    Integer countToday();

    /**
     * 生成验证代码
     *
     * @param account 账户
     * @return boolean
     */
    boolean generateVerifyCode(String account);

    /**
     * 绑定额外信息
     *
     * @param bindExtraInfoRequestVO 额外信息绑定请求参数
     * @return boolean
     */
    boolean bindExtraInfo(BindExtraInfoRequestVO bindExtraInfoRequestVO);

    /**
     * 更新用户信息
     *
     * @param request 请求
     * @return boolean
     */
    boolean updateUserInfo(UserInfoUpdateRequest request);

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
