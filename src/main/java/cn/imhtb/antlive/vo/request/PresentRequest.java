package cn.imhtb.antlive.vo.request;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author PinTeh
 * @date 2020/5/5
 */
@Data
public class PresentRequest {

    private Integer id;

    private String name;

    private String icon;

    private BigDecimal price;

    private int sort;

    private int disabled;
}
