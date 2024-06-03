package cn.imhtb.live.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 账单标注类型
 *
 * @author pinteh
 * @date 2023/02/16
 */
@Getter
@AllArgsConstructor
public enum BillMarkEnum {

    /**
     * 账单标注类型
     */
    LIVE_REWARD(0, "直播打赏"),
    VIDEO_REWARD(1, "视频打赏");

    private final int code;
    private final String desc;

}
