package cn.imhtb.live.modules.system.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author pinteh
 * @date 2025/4/29
 */
@Data
public class SystemUserDetail {

    private Integer id;

    private String username;

    private String account;

    private String email;

    private String mobile;

    private String avatar;

    private String nickname;

    private String sex;

    private String signature;

    private Integer roleId;

    private int disabled;

    private LocalDateTime createTime;

}
