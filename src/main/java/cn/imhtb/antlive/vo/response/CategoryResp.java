package cn.imhtb.antlive.vo.response;

import lombok.Data;

@Data
public class CategoryResp {

    private Integer id;

    private Integer parentId;

    private String name;

    private int sort;

}
