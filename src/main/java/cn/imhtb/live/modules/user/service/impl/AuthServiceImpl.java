package cn.imhtb.live.modules.user.service.impl;

import cn.imhtb.live.common.enums.AuthStatusEnum;
import cn.imhtb.live.common.enums.LiveRoomStatusEnum;
import cn.imhtb.live.common.holder.UserHolder;
import cn.imhtb.live.common.utils.CovertBeanUtil;
import cn.imhtb.live.mappers.AuthMapper;
import cn.imhtb.live.mappers.RoomMapper;
import cn.imhtb.live.modules.live.vo.AuthReqVo;
import cn.imhtb.live.modules.live.vo.AuthRespVo;
import cn.imhtb.live.pojo.database.AuthInfo;
import cn.imhtb.live.pojo.database.Room;
import cn.imhtb.live.modules.user.service.IAuthService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class AuthServiceImpl extends ServiceImpl<AuthMapper, AuthInfo> implements IAuthService {

    private final AuthMapper authMapper;

    private final RoomMapper roomMapper;


    public AuthServiceImpl(AuthMapper authMapper, RoomMapper roomMapper) {
        this.authMapper = authMapper;
        this.roomMapper = roomMapper;
    }

    @Override
    public void updateStatusByIds(Integer[] ids, Integer status) {
        //TODO 优化
        for (Integer id : ids) {
            AuthInfo authInfo = authMapper.selectById(id);
            AuthInfo update = new AuthInfo();
            //不设置UserId会自动为0？
            update.setUserId(authInfo.getUserId());
            update.setId(id);
            update.setStatus(status);
            authMapper.updateById(update);

            // 更新Room Status -1 => 0
            Room room = roomMapper.selectOne(new QueryWrapper<Room>().eq("user_id", authInfo.getUserId()));
            if (status.equals(AuthStatusEnum.PASS.getCode())) {
                Room updateRoom = new Room();
                updateRoom.setId(room.getId());
                updateRoom.setStatus(LiveRoomStatusEnum.STOP.getCode());
                roomMapper.updateById(updateRoom);
            } else if (status.equals(AuthStatusEnum.NO.getCode())) {
                Room updateRoom = new Room();
                updateRoom.setId(room.getId());
                updateRoom.setStatus(LiveRoomStatusEnum.UN_AUTH.getCode());
                roomMapper.updateById(updateRoom);
            }
        }
    }


    @Override
    public boolean submit(AuthReqVo authReqVo) {
        // todo 状态校验
        AuthRespVo status = this.getStatus();
        if (status != null){
            Integer status1 = status.getStatus();
            if (status1.equals(AuthStatusEnum.REJECT.getCode())){
                return false;
            }
        }
        AuthInfo authInfo = CovertBeanUtil.convert(authReqVo, AuthInfo.class);
        authInfo.setUserId(UserHolder.getUserId());
        authInfo.setStatus(AuthStatusEnum.NO.getCode());
        return save(authInfo);
    }

    @Override
    public AuthRespVo getStatus() {
        Integer userId = UserHolder.getUserId();
        AuthInfo authInfo = getOne(new LambdaQueryWrapper<AuthInfo>().eq(AuthInfo::getUserId, userId), false);
        if (Objects.isNull(authInfo)){
            return null;
        }
        AuthRespVo authRespVo = new AuthRespVo();
        authRespVo.setStatus(authInfo.getStatus());
        // todo
        authRespVo.setStatusName("");
        return authRespVo;
    }

}
