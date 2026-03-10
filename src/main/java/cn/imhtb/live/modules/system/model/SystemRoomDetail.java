package cn.imhtb.live.modules.system.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author pinteh
 * @date 2025/12/07
 */
@Data
public class SystemRoomDetail {

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

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    // 主播信息
    private String userNickname;

    private String userAvatar;

    private String userSignature;

    // 分类信息
    private String categoryName;

    private String categoryIcon;

}
