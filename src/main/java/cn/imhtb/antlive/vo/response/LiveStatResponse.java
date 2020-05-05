package cn.imhtb.antlive.vo.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author PinTeh
 * @date 2020/3/5
 */
@Data
public class LiveStatResponse {

   private Long totalTime;

   private List<LiveStat> liveStats;

   private Long total;

   @Data
   public static class LiveStat{

        private Integer id;

       private Long time;

       private LocalDateTime startTime;

        private LocalDateTime endTime;

    }


}
