package cn.imhtb.live.holder;

/**
 * @author pinteh
 * @date 2023/6/15
 */
public class UserHolder {

    private final static ThreadLocal<Integer> HOLDER = new ThreadLocal<>();

    public static Integer getUserId(){
        return HOLDER.get();
    }

    public static void setUserId(Integer userId){
        HOLDER.set(userId);
    }

    public static void remove(){
        HOLDER.remove();
    }

}
