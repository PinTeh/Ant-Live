package cn.imhtb.antlive.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author PinTeh
 * @date 2020/4/22
 */
@Data
public class FrontMenuItem implements Serializable {

    private Integer id;
    private Integer index;
    private String icon;
    private String path;
    private String title;
    private String label;
    private Integer hidden;
    private Integer sort;
    private Integer pid;
    private List<FrontMenuItem> children;

}
