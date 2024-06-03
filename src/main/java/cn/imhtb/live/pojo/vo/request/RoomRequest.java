package cn.imhtb.live.pojo.vo.request;

import lombok.Data;

/**
 * @author PinTeh
 */
@Data
public class RoomRequest {

    private Integer id;

    private String title;

    private String cover;

    private String introduce;

    private String notice;

    private String rtmpUrl;

    private int categoryId;

}
