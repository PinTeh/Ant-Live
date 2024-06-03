package cn.imhtb.live.pojo.vo.request;

import lombok.Data;

/**
 * @author PinTeh
 * @date 2020/4/30
 */
@Data
public class MenuRequest {

    private Integer id;

    private Integer index;

    private String icon;

    private String path;

    private String label;

    private Integer pid;

    private Integer sort;

    private Integer hidden;
}
