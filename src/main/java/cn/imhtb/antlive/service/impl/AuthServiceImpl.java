package cn.imhtb.antlive.service.impl;

import cn.imhtb.antlive.common.Constants;
import cn.imhtb.antlive.entity.AuthInfo;
import cn.imhtb.antlive.entity.Room;
import cn.imhtb.antlive.mappers.AuthMapper;
import cn.imhtb.antlive.mappers.RoomMapper;
import cn.imhtb.antlive.service.IAuthService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl extends ServiceImpl<AuthMapper, AuthInfo> implements IAuthService {

    @Autowired
    private AuthMapper authMapper;

    @Autowired
    private RoomMapper roomMapper;

    @Override
    public void updateStatusByIds(Integer[] ids, Integer status) {
        //TODO 优化
        for (Integer id : ids) {
            AuthInfo authInfo = authMapper.selectById(id);
            AuthInfo update = new AuthInfo();
            update.setId(id);
            update.setStatus(status);
            authMapper.updateById(update);

            // 更新Room Status -1 => 0
            Room room = roomMapper.selectOne(new QueryWrapper<Room>().eq("user_id",authInfo.getUserId()));
            if (status.equals(Constants.AuthStatus.YES.getCode())){
                Room updateRoom = new Room();
                updateRoom.setId(room.getId());
                updateRoom.setStatus(Constants.LiveStatus.STOP.getCode());
                roomMapper.updateById(updateRoom);
            }else if (status.equals(Constants.AuthStatus.NO.getCode())){
                Room updateRoom = new Room();
                updateRoom.setId(room.getId());
                updateRoom.setStatus(Constants.LiveStatus.UNAUTH.getCode());
                roomMapper.updateById(updateRoom);
            }
        }
    }
}
