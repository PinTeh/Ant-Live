package cn.imhtb.live.exception.base;

/**
 * @author pinteh
 */
public interface IErrorCode {

    /**
     * 获取错误码
     *
     * @return 错误码
     */
    int getCode();

    /**
     * 获取错误信息
     *
     * @return 错误信息
     */
    String getMsg();

}
