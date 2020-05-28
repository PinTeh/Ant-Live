package cn.imhtb.antlive.vo.request;

import lombok.Data;

/**
 * @author PinTeh
 * @date 2020/5/27
 */
@Data
public class VideoRequest {

    private Integer id;

    private String fileId;

    private String videoUrl;

    private String coverUrl;

    private String type;

    private Integer userId;

    private Integer videoCategoryId;

    private String title;

    private Integer status;
}
