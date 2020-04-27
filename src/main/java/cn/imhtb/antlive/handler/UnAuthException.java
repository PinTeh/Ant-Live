package cn.imhtb.antlive.handler;

/**
 * @author PinTeh
 */
public class UnAuthException extends RuntimeException {
    public UnAuthException(String message) {
        super(message);
    }
}
