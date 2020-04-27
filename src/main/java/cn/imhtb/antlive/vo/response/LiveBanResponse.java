package cn.imhtb.antlive.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

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
