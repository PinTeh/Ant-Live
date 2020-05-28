package cn.imhtb.antlive.vo.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
