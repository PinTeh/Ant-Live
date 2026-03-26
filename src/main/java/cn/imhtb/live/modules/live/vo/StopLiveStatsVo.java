package cn.imhtb.live.modules.live.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 停播统计信息
 *
 * @author pinteh
 * @date 2026/3/26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StopLiveStatsVo {

    /**
     * 开心果流水
     */
    private BigDecimal presentAmount;

    /**
     * 弹幕数
     */
    private Long danMuCount;

    /**
     * 累计观看
     */
    private Long totalViewCount;

    /**
     * 直播时长，单位秒
     */
    private Long liveDurationSeconds;

}
