package cn.imhtb.live.pojo.vo.response;

import lombok.Data;

@Data
public class CategoryResp {

    private Integer id;

    private Integer parentId;

    private String name;

    private String icon;

    private int sort;

}
