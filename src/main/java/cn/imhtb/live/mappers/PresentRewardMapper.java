package cn.imhtb.live.mappers;

import cn.imhtb.live.modules.live.vo.RewardRespVo;
import cn.imhtb.live.pojo.database.PresentReward;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @author PinTeh
 * @date 2020/3/25
 */
public interface PresentRewardMapper extends BaseMapper<PresentReward> {

    /**
     * 分页查询礼物清单数据
     *
     * @param page 分页
     * @param wrapper 查询条件
     * @return 礼物清单数据
     */
    Page<RewardRespVo> pageRewardRespVo(Page<RewardRespVo> page, @Param(Constants.WRAPPER) Wrapper<RewardRespVo> wrapper);

}
