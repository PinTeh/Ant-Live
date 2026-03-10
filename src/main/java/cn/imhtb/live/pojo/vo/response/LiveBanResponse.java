package cn.imhtb.live.pojo.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author PinTeh
 * @date 2020/4/15
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LiveBanResponse {

    private Integer roomId;

    private Integer userId;

    private LocalDateTime resumeTime;

    private LocalDateTime createTime;

    private String reason;

    private Integer status;
}
