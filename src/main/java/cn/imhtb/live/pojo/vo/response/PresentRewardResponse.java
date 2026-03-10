package cn.imhtb.live.pojo.vo.response;

import cn.imhtb.live.pojo.database.PresentReward;

/**
 * @author PinTeh
 * @date 2020/4/8
 */
public class PresentRewardResponse extends PresentReward {

    private String presentName;

    public String getPresentName() {
        return presentName;
    }

    public void setPresentName(String presentName) {
        this.presentName = presentName;
    }
}
