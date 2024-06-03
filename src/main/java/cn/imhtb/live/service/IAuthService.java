package cn.imhtb.live.service;

import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.pojo.AuthInfo;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IAuthService extends IService<AuthInfo> {
    void updateStatusByIds(Integer[] ids, Integer status);

    ApiResponse saveAndCheck(AuthInfo authInfo);
}
