package cn.imhtb.live.mappers;

import cn.imhtb.live.modules.system.model.SystemMessageDetail;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.imhtb.live.pojo.database.Message;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
* @author pinteh
*/
public interface MessageMapper extends BaseMapper<Message> {


    /**
     * 分页查询详情
     *
     * @param page 分页
     * @param wrapper 条件
     * @return 分页数据
     */
    Page<SystemMessageDetail> pageDetail(@Param("page") Page<SystemMessageDetail> page, @Param(Constants.WRAPPER) QueryWrapper<SystemMessageDetail> wrapper);

}
