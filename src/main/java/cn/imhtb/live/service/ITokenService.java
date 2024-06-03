package cn.imhtb.live.service;

import io.jsonwebtoken.Claims;

/**
 * @author pinteh
 */
public interface ITokenService {

    /**
     * 获取认证信息
     *
     * @return {@link Claims}
     */
    Claims get();

    /**
     * 获取用户id
     *
     * @return {@link Integer}
     */
    Integer getUserId();

}
