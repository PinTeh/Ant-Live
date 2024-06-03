package cn.imhtb.live.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 账单收支类型
 *
 * @author pinteh
 * @date 2023/02/16
 */
@Getter
@AllArgsConstructor
public enum BillTypeEnum {

    /**
     * 账单收支类型
     */
    INCOME(0, "收入"),
    OUTLAY(1, "支出");

    private final int code;
    private final String desc;

}
