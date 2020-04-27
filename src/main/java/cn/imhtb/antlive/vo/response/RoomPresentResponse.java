package cn.imhtb.antlive.vo.response;

import cn.imhtb.antlive.entity.database.RoomPresent;

/**
 * @author PinTeh
 * @date 2020/4/8
 */
public class RoomPresentResponse extends RoomPresent {

    private String presentName;

    public String getPresentName() {
        return presentName;
    }

    public void setPresentName(String presentName) {
        this.presentName = presentName;
    }
}
