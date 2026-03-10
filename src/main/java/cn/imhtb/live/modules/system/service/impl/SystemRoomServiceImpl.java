package cn.imhtb.live.modules.system.service.impl;

import cn.imhtb.live.common.PageData;
import cn.imhtb.live.mappers.RoomMapper;
import cn.imhtb.live.modules.infra.model.PageQuery;
import cn.imhtb.live.modules.infra.service.impl.BaseServiceImpl;
import cn.imhtb.live.modules.system.model.SystemRoomDetail;
import cn.imhtb.live.modules.system.model.SystemRoomQuery;
import cn.imhtb.live.modules.system.model.SystemRoomUpdate;
import cn.imhtb.live.modules.system.service.ISystemRoomService;
import cn.imhtb.live.pojo.database.Room;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pinteh
 * @date 2025/12/07
 */
@Service
public class SystemRoomServiceImpl extends BaseServiceImpl<RoomMapper, Room, SystemRoomDetail, SystemRoomQuery, SystemRoomDetail, SystemRoomUpdate> implements ISystemRoomService {

    @Autowired
    private RoomMapper roomMapper;

    @Override
    public PageData<SystemRoomDetail> page(SystemRoomQuery query, PageQuery pageQuery) {
        // 构建查询条件
        QueryWrapper<Room> queryWrapper = buildQueryWrapper(query);
        
        // 使用自定义的 pageDetail 方法查询，会关联主播信息
        Page<SystemRoomDetail> page = roomMapper.pageDetail(
            new Page<>(pageQuery.getPageNo(), pageQuery.getPageSize()),
            queryWrapper
        );
        
        // 封装返回结果
        PageData<SystemRoomDetail> pageData = new PageData<>();
        pageData.setTotal(page.getTotal());
        pageData.setList(page.getRecords());
        return pageData;
    }

    /**
     * 从父类抽取出 buildQueryWrapper 方法
     */
    private QueryWrapper<Room> buildQueryWrapper(SystemRoomQuery query) {
        QueryWrapper<Room> queryWrapper = new QueryWrapper<>();
        if (query.getId() != null) {
            queryWrapper.eq("r.id", query.getId());
        }
        if (query.getUserId() != null) {
            queryWrapper.eq("r.user_id", query.getUserId());
        }
        if (query.getTitle() != null && !query.getTitle().isEmpty()) {
            queryWrapper.like("r.title", query.getTitle());
        }
        if (query.getStatus() != null) {
            queryWrapper.eq("r.status", query.getStatus());
        }
        if (query.getCategoryId() != null) {
            queryWrapper.eq("r.category_id", query.getCategoryId());
        }
        if (query.getDisabled() != null) {
            queryWrapper.eq("r.disabled", query.getDisabled());
        }
        return queryWrapper;
    }
}
