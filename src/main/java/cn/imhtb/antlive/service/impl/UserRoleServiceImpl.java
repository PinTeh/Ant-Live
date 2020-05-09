package cn.imhtb.antlive.service.impl;

import cn.imhtb.antlive.entity.database.UserRole;
import cn.imhtb.antlive.mappers.UserRoleMapper;
import cn.imhtb.antlive.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author PinTeh
 * @date 2020/5/9
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {
}
