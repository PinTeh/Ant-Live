package cn.imhtb.live.service.impl;

import cn.imhtb.live.common.PageData;
import cn.imhtb.live.enums.DisabledStatusEnum;
import cn.imhtb.live.enums.LiveRoomStatusEnum;
import cn.imhtb.live.enums.WatchTypeEnum;
import cn.imhtb.live.exception.UnAuthException;
import cn.imhtb.live.mappers.RoomMapper;
import cn.imhtb.live.modules.live.service.ICategoryService;
import cn.imhtb.live.modules.user.service.IUserService;
import cn.imhtb.live.pojo.Room;
import cn.imhtb.live.pojo.User;
import cn.imhtb.live.pojo.database.Category;
import cn.imhtb.live.pojo.database.Watch;
import cn.imhtb.live.pojo.vo.RoomExtraInfo;
import cn.imhtb.live.pojo.vo.request.RoomInfoSaveRequest;
import cn.imhtb.live.modules.live.vo.RoomRespVo;
import cn.imhtb.live.service.IRoomService;
import cn.imhtb.live.service.ITokenService;
import cn.imhtb.live.service.IWatchService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author PinTeh
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements IRoomService {

    private final IUserService userService;
    private final ITokenService tokenService;
    private final IWatchService watchService;
    private final ICategoryService categoryService;

    @Override
    public void updateCover(String coverUrl) {
        Integer userId = tokenService.getUserId();
        Room room = new Room();
        room.setCover(coverUrl);
        update(room, new LambdaQueryWrapper<Room>().eq(Room::getUserId, userId));
    }

    @Override
    public boolean saveInfo(RoomInfoSaveRequest request) {
        Room room = getOne(new LambdaQueryWrapper<Room>()
                .eq(Room::getUserId, tokenService.getUserId()), false);
        if (room == null) {
            return false;
        }
        Room updateRoom = new Room();
        updateRoom.setId(room.getId());
        updateRoom.setTitle(request.getTitle());
        updateRoom.setCover(request.getCover());
        updateRoom.setCategoryId(request.getCid());
        updateRoom.setNotice(request.getNotice());
        updateRoom.setIntroduce(request.getIntroduce());
        return updateById(updateRoom);
    }

    @Override
    public PageData<RoomRespVo> getLivingRooms(Integer cid, Integer pageNo, Integer pageSize) {
        Page<Room> page = this.page(new Page<>(pageNo, pageSize), new LambdaQueryWrapper<Room>()
                .eq(cid != null, Room::getCategoryId, cid)
                .eq(Room::getStatus, LiveRoomStatusEnum.LIVING.getCode())
                .eq(Room::getDisabled, DisabledStatusEnum.YES.getCode()));

        List<RoomRespVo> collect = page.getRecords().stream()
                .map(this::packageRoomResponse)
                .collect(Collectors.toList());

        PageData<RoomRespVo> pageData = new PageData<>();
        pageData.setList(collect);
        pageData.setTotal(page.getTotal());
        return pageData;
    }

    @Override
    public RoomRespVo getRoomInfo(Integer rid) {
        Room room = getById(rid);
        if (room == null){
            return null;
        }
        return this.packageRoomResponse(room);
    }

    @Override
    public RoomExtraInfo getExtraInfo(Integer rid) {
        RoomExtraInfo roomExtraInfo = new RoomExtraInfo();
        roomExtraInfo.setFollow(false);
        try {
            Integer userId = tokenService.getUserId();
            long count = watchService.count(new LambdaQueryWrapper<Watch>()
                    .eq(Watch::getRoomId, rid)
                    .eq(Watch::getUserId, userId)
                    .eq(Watch::getWatchType, WatchTypeEnum.FOLLOW.getCode()));
            if (count > 0){
                roomExtraInfo.setFollow(true);
            }
        } catch (UnAuthException e) {
            // 未登录时，不影响正常进入直播间流程
            roomExtraInfo.setFollow(false);
        }
        return roomExtraInfo;
    }

    private RoomRespVo packageRoomResponse(Room room) {
        //TODO
        User user = userService.getById(room.getUserId());
        Category category = categoryService.getById(room.getCategoryId());
        RoomRespVo response = new RoomRespVo();
        response.setId(room.getId());
        response.setTitle(room.getTitle());
        response.setPullUrl(room.getPullUrl());
        response.setCover(room.getCover());
        if (user != null) {
            response.setUserInfo(new RoomRespVo.UserInfoVo(user.getId(), user.getNickname(), user.getAvatar()));
        }
        if (category != null) {
            response.setCategoryInfo(new RoomRespVo.CategoryInfoVo(category.getId(), category.getName()));
        }
        response.setStatus(room.getStatus());
        return response;
    }

}
