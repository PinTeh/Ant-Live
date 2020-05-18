package cn.imhtb.antlive.service;

import cn.imhtb.antlive.common.ApiResponse;
import cn.imhtb.antlive.entity.AuthInfo;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IAuthService extends IService<AuthInfo> {
    void updateStatusByIds(Integer[] ids, Integer status);

    ApiResponse saveAndCheck(AuthInfo authInfo);
}
