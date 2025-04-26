package cn.imhtb.live.modules.base.service;

import cn.imhtb.live.modules.base.model.CategoryResp;
import cn.imhtb.live.modules.base.model.VerifyCodeReq;

import java.util.List;

/**
 * @author pinteh
 * @date 2025/4/12
 */
public interface ICommonService {

    /**
     * 用于获取分类信息列表
     *
     * @return 分类信息
     */
    List<CategoryResp> getCategories();

    /**
     * 发送验证码
     *
     * @param req 验证码类型
     * @return 发送结果
     */
    Boolean sendVerifyCode(VerifyCodeReq req);

}
