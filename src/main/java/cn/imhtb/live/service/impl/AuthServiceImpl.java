package cn.imhtb.live.service.impl;

import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.pojo.AuthInfo;
import cn.imhtb.live.pojo.Room;
import cn.imhtb.live.enums.AuthStatusEnum;
import cn.imhtb.live.enums.LiveRoomStatusEnum;
import cn.imhtb.live.mappers.AuthMapper;
import cn.imhtb.live.mappers.RoomMapper;
import cn.imhtb.live.service.IAuthService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ocr.v20181119.OcrClient;
import com.tencentcloudapi.ocr.v20181119.models.IDCardOCRRequest;
import com.tencentcloudapi.ocr.v20181119.models.IDCardOCRResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
public class AuthServiceImpl extends ServiceImpl<AuthMapper, AuthInfo> implements IAuthService {

    private final AuthMapper authMapper;

    private final RoomMapper roomMapper;

    private final OcrClient ocrClient;

    public AuthServiceImpl(AuthMapper authMapper, RoomMapper roomMapper, OcrClient ocrClient) {
        this.authMapper = authMapper;
        this.roomMapper = roomMapper;
        this.ocrClient = ocrClient;
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
        //正面
        String params = "{\"ImageUrl\":\"" + authInfo.getPositiveUrl() + "\"}";
        IDCardOCRRequest req = IDCardOCRRequest.fromJsonString(params, IDCardOCRRequest.class);
        try {
            IDCardOCRResponse response = ocrClient.IDCardOCR(req);
            log.info("自动审核:" + JSON.toJSONString(response));
            String idNum = response.getIdNum();
            String name = response.getName();
            String advancedInfo = response.getAdvancedInfo();
            String ret = handleAdvanceInfo(advancedInfo);
            if (!StringUtils.isEmpty(ret)) {
                log.info("自动否决");
                authInfo.setStatus(AuthStatusEnum.REJECT.getCode());
                authInfo.setRejectReason(ret);
                authMapper.insert(authInfo);
                return ApiResponse.ofSuccess();
            }
            // 自动核对信息
            if (idNum.equals(authInfo.getCardNo()) && name.equals(authInfo.getRealName())) {
                log.info("自动审核通过");
                authInfo.setStatus(AuthStatusEnum.AUTO_PASS.getCode());
            } else {
                log.info("自动否决");
                authInfo.setStatus(AuthStatusEnum.REJECT.getCode());
                authInfo.setRejectReason("自动否决");
            }
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }

        // 反面
        params = "{\"ImageUrl\":\"" + authInfo.getReverseUrl() + "\"}";
        IDCardOCRRequest ocrRequest = IDCardOCRRequest.fromJsonString(params, IDCardOCRRequest.class);
        try {
            IDCardOCRResponse response = ocrClient.IDCardOCR(ocrRequest);
            String advancedInfo = response.getAdvancedInfo();
            log.info("高级信息:" + advancedInfo);
            String ret = handleAdvanceInfo(advancedInfo);
            if (!StringUtils.isEmpty(ret)) {
                log.info("自动否决:" + ret);
                authInfo.setStatus(AuthStatusEnum.REJECT.getCode());
                authInfo.setRejectReason(ret);
                authMapper.insert(authInfo);
                return ApiResponse.ofSuccess();
            }
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
        authMapper.insert(authInfo);
        return ApiResponse.ofSuccess();
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
