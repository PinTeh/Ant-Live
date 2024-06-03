package cn.imhtb.live.service.impl;

import cn.imhtb.live.common.PageData;
import cn.imhtb.live.enums.WatchTypeEnum;
import cn.imhtb.live.mappers.WatchMapper;
import cn.imhtb.live.modules.user.service.IUserService;
import cn.imhtb.live.pojo.Room;
import cn.imhtb.live.pojo.User;
import cn.imhtb.live.pojo.database.Watch;
import cn.imhtb.live.pojo.vo.request.RelationSaveRequest;
import cn.imhtb.live.pojo.vo.response.WatchResponse;
import cn.imhtb.live.service.IRoomService;
import cn.imhtb.live.service.ITokenService;
import cn.imhtb.live.service.IWatchService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author PinTeh
 * @date 2020/3/18
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class IWatchServiceImpl extends ServiceImpl<WatchMapper, Watch> implements IWatchService {

    @Autowired
    @Lazy
    private IRoomService roomService;
    private final IUserService userService;
    private final ITokenService tokenService;

    @Override
    public Boolean saveRelation(RelationSaveRequest request) {
        LambdaQueryWrapper<Watch> wrapper = new LambdaQueryWrapper<Watch>()
                .eq(Watch::getRoomId, request.getRid());

        // 关注
        if (request.getAct() == 1){
            Integer userId = tokenService.getUserId();
            wrapper.eq(Watch::getUserId, userId);
            wrapper.eq(Watch::getWatchType, WatchTypeEnum.FOLLOW.getCode());
            long count = count(wrapper);
            if (count == 0){
                Watch watch = Watch.builder()
                        .userId(userId)
                        .roomId(request.getRid())
                        .watchType(WatchTypeEnum.FOLLOW.getCode())
                        .build();
                return save(watch);
            }
        }
        // 取消关注
        if (request.getAct() == 2){
            Integer userId = tokenService.getUserId();
            wrapper.eq(Watch::getUserId, userId);
            wrapper.eq(Watch::getWatchType, WatchTypeEnum.FOLLOW.getCode());
            return remove(wrapper);
        }
        // 历史记录
        if (request.getAct() == 3){
            Integer userId = null;
            try {
                userId = tokenService.getUserId();
            } catch (Exception e) {
                // ignore
            }
            wrapper.eq(Watch::getWatchType, WatchTypeEnum.HISTORY.getCode());
            long count = count(wrapper);
            if (count == 0){
                Watch watch = Watch.builder()
                        .userId(userId)
                        .roomId(request.getRid())
                        .watchType(WatchTypeEnum.HISTORY.getCode())
                        .build();
                return save(watch);
            }
        }
        return false;
    }

    @Override
    public PageData<WatchResponse> listWatches(Integer type, Integer limit, Integer page) {
        Integer userId = tokenService.getUserId();
        Page<Watch> watchPage = page(new Page<>(page, limit), new LambdaQueryWrapper<Watch>().eq(Watch::getUserId, userId)
                .eq(Watch::getWatchType, type)
                .orderByDesc(Watch::getId));

        PageData<WatchResponse> pageData = new PageData<>();
        pageData.setTotal(watchPage.getTotal());
        List<WatchResponse> watchResponses = packageWatch(watchPage.getRecords());
        pageData.setList(watchResponses);
        return pageData;
    }

    @Override
    public Boolean clearHistory() {
        Integer userId = tokenService.getUserId();
        return remove(new LambdaQueryWrapper<Watch>()
                .eq(Watch::getUserId, userId)
                .eq(Watch::getWatchType, WatchTypeEnum.HISTORY.getCode()));
    }

    private List<WatchResponse> packageWatch(List<Watch> watches) {
        if (watches.size() < 1) {
            return null;
        }
        List<WatchResponse> list = new ArrayList<>();
        List<Integer> ids = watches.stream().map(Watch::getRoomId).collect(Collectors.toList());
        List<Integer> uIds = roomService.listByIds(ids).stream().map(Room::getUserId).collect(Collectors.toList());
        List<String> nickNames = userService.listByIds(uIds).stream().map(User::getNickname).collect(Collectors.toList());
        for (int i = 0; i < watches.size(); i++) {
            WatchResponse response = new WatchResponse();
            response.setId(watches.get(i).getId());
            Room room = roomService.getById(watches.get(i).getRoomId());
            response.setCover(room.getCover());
            response.setName(nickNames.get(i));
            response.setTitle(room.getTitle());
            response.setRoomId(room.getId());
            list.add(response);
        }
        return list;
    }

}
