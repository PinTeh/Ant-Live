package cn.imhtb.live.modules.user.service;

import cn.imhtb.live.modules.live.vo.AuthReqVo;
import cn.imhtb.live.modules.live.vo.AuthRespVo;
import cn.imhtb.live.pojo.database.AuthInfo;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IAuthService extends IService<AuthInfo> {

    void updateStatusByIds(Integer[] ids, Integer status);

    /**
     * 提交身份认证
     *
     * @param authReqVo 身份认证信息
     * @return boolean 返回提交结果
     */
    boolean submit(AuthReqVo authReqVo);

    /**
     * 获取当前用户认证状态
     *
     * @return {@link AuthRespVo}
     */
    AuthRespVo getStatus();

}
