package cn.imhtb.live.pojo.vo.request;

import lombok.Data;

/**
 * @author PinTeh
 * @date 2020/4/19
 */
@Data
public class RoomInfoSaveRequest {

    private Integer rid;

    private String cover;

    private String title;

    private String notice;

    private Integer cid;
}
