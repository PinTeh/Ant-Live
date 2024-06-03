package cn.imhtb.live.pojo.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponse {

    private Integer id;

    private String title;

    private String cover;

    private UserInfo userInfo;

    private String liveUrl;

    private Integer status;

    private CategoryInfo categoryInfo;

    @Data
    @AllArgsConstructor
    public static class UserInfo {
        private Integer id;
        private String name;
        private String avatar;
    }

    @Data
    @AllArgsConstructor
    public static class CategoryInfo {
        private Integer id;
        private String name;
    }
}
