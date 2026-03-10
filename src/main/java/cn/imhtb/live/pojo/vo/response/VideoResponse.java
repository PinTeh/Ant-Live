package cn.imhtb.live.pojo.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author PinTeh
 * @date 2020/5/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoResponse {

    private Integer id;

    private String videoUrl;

    private String coverUrl;

    private String title;

    private UserInfo userInfo;

    @Data
    @AllArgsConstructor
    public static class UserInfo {
        private Integer id;
        private String name;
        private String avatar;
    }
}
