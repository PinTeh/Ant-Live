package cn.imhtb.live.service.impl;

import cn.imhtb.live.common.PageData;
import cn.imhtb.live.common.enums.WatchTypeEnum;
import cn.imhtb.live.mappers.RoomMapper;
import cn.imhtb.live.mappers.UserMapper;
import cn.imhtb.live.mappers.WatchMapper;
import cn.imhtb.live.pojo.database.Room;
import cn.imhtb.live.pojo.database.User;
import cn.imhtb.live.pojo.database.Watch;
import cn.imhtb.live.pojo.vo.response.WatchResponse;
import cn.imhtb.live.service.IWatchService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final RoomMapper roomMapper;
    private final UserMapper userMapper;

    @Override
    public PageData<WatchResponse> listWatches(Integer userId, Integer type, Integer limit, Integer page) {
        LambdaQueryWrapper<Watch> wrapper = new LambdaQueryWrapper<Watch>().eq(Watch::getUserId, userId)
                .eq(Watch::getWatchType, type)
                .orderByDesc(Watch::getId);
        Page<Watch> watchPage = page(new Page<>(page, limit), wrapper);
        PageData<WatchResponse> pageData = new PageData<>();
        pageData.setTotal(watchPage.getTotal());
        pageData.setList(packageWatch(watchPage.getRecords()));
        return pageData;
    }

    @Override
    public Boolean saveHistory(Integer userId, Integer roomId) {
        Long count = lambdaQuery().eq(Watch::getUserId, userId)
                .eq(Watch::getRoomId, roomId)
                .eq(Watch::getWatchType, WatchTypeEnum.HISTORY.getCode())
                .count();
        if (count != 0) {
            return false;
        }
        Watch watch = new Watch();
        watch.setUserId(userId);
        watch.setRoomId(roomId);
        watch.setWatchType(WatchTypeEnum.HISTORY.getCode());
        return save(watch);
    }

    @Override
    public Boolean follow(Integer userId, Integer roomId) {
        Long count = lambdaQuery().eq(Watch::getUserId, userId)
                .eq(Watch::getRoomId, roomId)
                .eq(Watch::getWatchType, WatchTypeEnum.FOLLOW.getCode())
                .count();
        if (count != 0) {
            return false;
        }
        Watch watch = new Watch();
        watch.setUserId(userId);
        watch.setRoomId(roomId);
        watch.setWatchType(WatchTypeEnum.FOLLOW.getCode());
        return save(watch);
    }

    @Override
    public Boolean unFollow(Integer userId, Integer roomId) {
        return lambdaUpdate().eq(Watch::getUserId, userId).eq(Watch::getRoomId, roomId).remove();
    }

    @Override
    public Boolean clearHistory(Integer userId) {
        return remove(new LambdaQueryWrapper<Watch>()
                .eq(Watch::getUserId, userId)
                .eq(Watch::getWatchType, WatchTypeEnum.HISTORY.getCode()));
    }


    private List<WatchResponse> packageWatch(List<Watch> watches) {
        if (watches.isEmpty()) {
            return null;
        }
        List<WatchResponse> list = new ArrayList<>();
        List<Integer> ids = watches.stream().map(Watch::getRoomId).collect(Collectors.toList());
        List<Integer> uIds = roomMapper.selectBatchIds(ids).stream().map(Room::getUserId).collect(Collectors.toList());
        List<String> nickNames = userMapper.selectBatchIds(uIds).stream().map(User::getNickname).collect(Collectors.toList());
        for (int i = 0; i < watches.size(); i++) {
            WatchResponse response = new WatchResponse();
            response.setId(watches.get(i).getId());
            Room room = roomMapper.selectById(watches.get(i).getRoomId());
            response.setCover(room.getCover());
            response.setName(nickNames.get(i));
            response.setTitle(room.getTitle());
            response.setRoomId(room.getId());
            list.add(response);
        }
        return list;
    }

}
