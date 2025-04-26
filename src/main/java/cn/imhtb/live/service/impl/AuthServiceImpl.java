package cn.imhtb.live.service.impl;

import cn.imhtb.live.common.ApiResponse;
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
import cn.imhtb.live.service.IAuthService;
import com.alibaba.fastjson.JSON;
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
    public ApiResponse saveAndCheck(AuthInfo authInfo) {
        return ApiResponse.ofSuccess();
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

    /**
     * WarnInfos，告警信息，Code 告警码列表和释义：
     * -9100 身份证有效日期不合法告警，
     * -9101 身份证边框不完整告警，
     * -9102 身份证复印件告警，
     * -9103 身份证翻拍告警，
     * -9105 身份证框内遮挡告警，
     * -9104 临时身份证告警，
     * -9106 身份证 PS 告警。
     */
    private String handleAdvanceInfo(String advanceInfo) {
        StringBuilder ret = new StringBuilder();
        OcrAdvanceInfo ocrAdvanceInfo = JSON.parseObject(advanceInfo, OcrAdvanceInfo.class);
        Integer[] warnInfos = ocrAdvanceInfo.getWarnInfos();
        if (warnInfos != null) {
            for (Integer warnInfo : warnInfos) {
                switch (warnInfo) {
                    case -9100:
                        ret.append("身份证有效日期不合法");
                        break;
                    case -9101:
                        ret.append("身份证边框不完整");
                        break;
                    case -9102:
                        ret.append("身份证复印件告警");
                        break;
                    case -9103:
                        ret.append("身份证翻拍告警");
                        break;
                    case -9104:
                        ret.append("临时身份证告警");
                        break;
                    case -9105:
                        ret.append("身份证框内遮挡告警");
                        break;
                    case -9106:
                        ret.append("身份证 PS 告警");
                        break;
                }
            }
        }
        return ret.toString();
    }


    static class OcrAdvanceInfo {
        private Integer[] warnInfos;

        public Integer[] getWarnInfos() {
            return warnInfos;
        }

    }
}
