package cn.imhtb.live.pojo.vo.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author PinTeh
 * @date 2020/5/28
 */
@Data
@Builder
public class BillTotalResponse {


    private BigDecimal number;

    private LocalDate date;

}
