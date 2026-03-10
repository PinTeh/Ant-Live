package cn.imhtb.live.modules.system.model;

import lombok.Data;

/**
 * @author pinteh
 * @date 2025/12/07
 */
@Data
public class SystemRoomUpdate {

    private Integer id;

    private Integer userId;

    private String title;

    private String cover;

    private String secret;

    private String introduce;

    private String notice;

    private String rtmpUrl;

    private Integer disabled;

    private Integer status;

    private Integer categoryId;

    private String pushUrl;

    private String pullUrl;

}
