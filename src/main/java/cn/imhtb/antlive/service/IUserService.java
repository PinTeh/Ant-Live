package cn.imhtb.antlive.service;

import cn.imhtb.antlive.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IUserService extends IService<User> {

    User login(String account, String password);

    boolean[] getSecurityInfo(Integer userId);

    void updateStatusByIds(Integer[] ids,Integer status);
}
