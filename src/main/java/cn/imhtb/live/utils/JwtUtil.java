package cn.imhtb.live.utils;

import cn.imhtb.live.constants.JwtConstant;
import cn.imhtb.live.pojo.User;
import cn.imhtb.live.exception.UnAuthException;
import com.alipay.api.internal.util.StringUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author pinteh
 */
public class JwtUtil {


    /**
     * 发行者
     */
    private static final String SUBJECT = "Ant Live";

    /**
     * 固定header头部key值
     */
    private static final String HEADER_KEY = JwtConstant.TOKEN_HEADER;

    /**
     * 过期延时 90min
     */
//    private static final long EXPIRE_SECONDS = 90 * 1000 * 60;
    private static final long EXPIRE_SECONDS = 24 * 60 * 1000 * 60;

    /**
     * 密钥
     */
    private static final String SECRET = "+/UElOVEVILUFOVC1MSVZF";

    /**
     * 创建token
     */
    public static String createToken(User user) {
        //更多校验
        if (user == null) {
            return null;
        }

        String token = Jwts.builder().setSubject(SUBJECT)
                .setId(String.valueOf(user.getId()))
                .claim("username", user.getNickname())
                .claim("imageUrl", "ttt")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_SECONDS))
                .signWith(SignatureAlgorithm.HS256, SECRET).compact();

        return JwtConstant.TOKEN_PREFIX + token;

    }

    public static String createTokenByParams(Integer id, String nickName, String account) {

        String token = Jwts.builder().setSubject(SUBJECT)
                .setId(String.valueOf(id))
                .claim("username", nickName)
                .setIssuedAt(new Date())
                .setSubject(account)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_SECONDS))
                .signWith(SignatureAlgorithm.HS256, SECRET).compact();

        return JwtConstant.TOKEN_PREFIX + token;
    }

    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.decode(SECRET);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    /**
     * 验证token
     */
    public static Claims verifyJwt(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(JwtConstant.TOKEN_PREFIX, ""))
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public static Integer getId(HttpServletRequest request) {
        String token = request.getHeader(HEADER_KEY);
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(JwtConstant.TOKEN_PREFIX, ""))
                    .getBody();
            if (body == null) {
                throw new UnAuthException("验证token失败");
            }
            return Integer.valueOf(body.getId());
        } catch (Exception e) {
            throw new UnAuthException("验证token失败");
        }
    }

    public static Integer getId(String token) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(JwtConstant.TOKEN_PREFIX, ""))
                    .getBody();
            if (body == null) {
                throw new UnAuthException("验证token失败");
            }
            return Integer.valueOf(body.getId());
        } catch (Exception e) {
            throw new UnAuthException("验证token失败");
        }
    }

    public static Integer getIdNoError(String token) {
        if (StringUtils.isEmpty(token) || "null".equals(token)) {
            return -1;
        }
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(JwtConstant.TOKEN_PREFIX, ""))
                    .getBody();
            return Integer.valueOf(body.getId());
        } catch (Exception e) {
            return -1;
        }
    }

    public static String getHeaderKey() {
        return HEADER_KEY;
    }

    public static String getUsernameByToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(JwtConstant.TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();
    }
}
