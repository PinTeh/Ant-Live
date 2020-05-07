package cn.imhtb.antlive.utils;

import cn.imhtb.antlive.entity.User;
import cn.imhtb.antlive.handler.UnAuthException;
import com.alipay.api.internal.util.StringUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class JwtUtils {


    /**
     * 发行者
     */
    private static final String SUBJECT = "Ant Live";

    private static final String HEADER_KEY = "token";

    /**
     * 过期延时
     */
    private static final long EXPIRE_SECONDS = 1000 * 60 * 60 * 24;

    /**
     * 密钥
     */
    private static final String SECRET = "tomato24523467452245234624562345";

    /**
     * 创建token
     */
    public static String createToken(User user) {
        //更多校验
        if (user == null) {
            return null;
        }

        return Jwts.builder().setSubject(SUBJECT)
                .setId(String.valueOf(user.getId()))
                .claim("username", user.getNickName())
                .claim("imageUrl", "ttt")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_SECONDS))
                .signWith(SignatureAlgorithm.HS256, SECRET).compact();

    }

    public static String createTokenByParams(Integer id,String nickName,String account) {

        return Jwts.builder().setSubject(SUBJECT)
                .setId(String.valueOf(id))
                .claim("username", nickName)
                .setIssuedAt(new Date())
                .setSubject(account)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_SECONDS))
                .signWith(SignatureAlgorithm.HS256, SECRET).compact();

    }

    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.decode(SECRET);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    /**
     * 验证token
     */
    public static Claims verifyJWT(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
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
                    .parseClaimsJws(token)
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
                    .parseClaimsJws(token)
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
                    .parseClaimsJws(token)
                    .getBody();
            return Integer.valueOf(body.getId());
        }catch (Exception e){
            return -1;
        }
    }

    public static String getHeaderKey() {
        return HEADER_KEY;
    }

    public static String getUsernameByToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
