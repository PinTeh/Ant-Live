package cn.imhtb.live.pojo.tencent;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author PinTeh
 * @date 2020/4/14
 */
@Data
public class ShotRuleResponse {


    /**
     * ocrMsg :
     * type : [2]
     * confidence : 0
     * normalScore : 2
     * hotScore : 97
     * pornScore : 0
     * screenshotTime : 1575513174
     * level : 0
     * img : http://test-10000.cos.ap-shanghai.myqcloud.com/2019-12-05/teststream-screenshot-10-32-54-960x540.jpg
     * abductionRisk : []
     * faceDetails : []
     * sendTime : 1575513176
     * illegalScore : 0
     * polityScore : 0
     * similarScore : 0
     * terrorScore : 0
     * tid : 20001
     * streamId : teststream
     * channelId : teststream
     * stream_param : txSecret=40f38f69f574fd51126c421a3d96c374&txTime=5DEBEC80
     * app : testlive.myqcloud.com
     * appname : live
     * appid : 10000
     */

    private String ocrMsg;
    private int confidence;
    private int normalScore;
    private int hotScore;
    private int pornScore;
    private int screenshotTime;
    private int level;
    private String img;
    private int sendTime;
    private int illegalScore;
    private int polityScore;
    private int similarScore;
    private int terrorScore;
    private int tid;
    private String streamId;
    private String channelId;
    private String stream_param;
    private String app;
    private String appname;
    private int appid;
    private List<Integer> type;
    private List<AbductionRisk> abductionRisk;
    private List<FaceDetails> faceDetails;

    @Getter
    @Setter
    private static class AbductionRisk {
        private int level;
        private int number;
    }

    @Getter
    @Setter
    private static class FaceDetails {
        private int gender;
        private int age;
        private int expression;
        private int beauty;
        private int x;
        private int y;
        private int width;
        private int height;
    }
}
