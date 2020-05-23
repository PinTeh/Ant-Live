package cn.imhtb.antlive.entity.database;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author PinTeh
 * @date 2020/5/23
 */
@Data
public class BanRecord {

    private Integer id;

    private Integer roomId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private LocalDateTime resumeTime;

    private Integer status;

    private String mark;

    private LocalDateTime startTime;

    private String reason;
}
