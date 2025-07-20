package cn.imhtb.live.modules.live.service.impl;

import cn.hutool.core.util.ReflectUtil;
import cn.imhtb.live.common.PageData;
import cn.imhtb.live.mappers.MessageMapper;
import cn.imhtb.live.modules.infra.model.PageQuery;
import cn.imhtb.live.modules.live.service.IMessageService;
import cn.imhtb.live.modules.system.model.SystemMessageDetail;
import cn.imhtb.live.modules.system.model.SystemMessageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

/**
 * @author pinteh
 * @date 2025/7/20
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MessageServiceImpl implements IMessageService {

    private final MessageMapper messageMapper;

    @Override
    public PageData<SystemMessageDetail> pageDetail(PageQuery pageQuery, SystemMessageQuery query) {
        Field[] fields = ReflectUtil.getFields(query.getClass());
//        QueryWrapper<SystemMessageDetail> queryWrapper = QueryWrapperHelper.getQueryWrapper(query, Arrays.asList(fields), new QueryWrapper<>());
        Page<SystemMessageDetail> page = messageMapper.pageDetail(new Page<>(pageQuery.getPageNo(), pageQuery.getPageSize()), null);
        PageData<SystemMessageDetail> pageData = new PageData<>();
        pageData.setTotal(page.getTotal());
        pageData.setList(page.getRecords());
        return pageData;
    }

}
