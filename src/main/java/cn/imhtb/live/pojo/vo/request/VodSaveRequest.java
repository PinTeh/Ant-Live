package cn.imhtb.live.pojo.vo.request;

import lombok.Data;

/**
 * @author PinTeh
 * @date 2020/5/25
 */
@Data
public class VodSaveRequest {

    // 回调结果说明
    // type doneResult = {
    //   fileId: string,
    //   video: {
    //     url: string
    //   },
    //   cover: {
    //     url: string
    //   }
    // }

    private String fileId;

    private String videoUrl;

    private String coverUrl;

    private String name;

    private Integer categoryId;
}
