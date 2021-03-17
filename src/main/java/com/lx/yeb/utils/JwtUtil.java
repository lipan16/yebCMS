package com.lx.yeb.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName JWT
 * @Description JWT工具类
 * @Author lipan
 * @Date 2021/3/17 15:26
 * @Version 1.0
 */
public class JwtUtil{
    // 密钥
    private static final String SECRET     = "suokou@hunan";
    // 过期时间（单位：秒）
    private static final long   EXPIRATION = 3600L;

    /**
     * fetch 创建token
     *
     * @param userId, username, password
     * @return java.lang.String
     * @author lipan
     * @date 2021/3/17 15:43
     */

    public static String createToken(Integer userId, String username, String password){
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String token = JWT.create()
                          // 添加头部
                          .withHeader(map)
                          // 放入用户的id
                          .withAudience(String.valueOf(userId))
                          // 可以将基本信息放到claims中
                          .withClaim("username", username).withClaim("password", password)
                          // 超时设置,设置过期的日期
                          .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION * 1000))
                          // 签发时间
                          .withIssuedAt(new Date())
                          // SECRET加密
                          .sign(Algorithm.HMAC256(SECRET));
        return token;
    }

    /**
     * fetch 校验token
     * @param token
     * @return boolean
     * @author lipan
     * @date 2021/3/17 15:46
     */
    public static boolean verifyToken(String token){
        try{
            JWTVerifier verifier   = JWT.require(Algorithm.HMAC256(SECRET)).build();
            DecodedJWT  decodedJWT = verifier.verify(token);
            return null != decodedJWT;
        }catch(JWTVerificationException e){
            // e.printStackTrace();
            return false;
        }
    }


    /**
     * fetch 获取Token信息
     * @param token
     * @return com.auth0.jwt.interfaces.DecodedJWT
     * @author lipan
     * @date 2021/3/17 15:55
     */
    public static DecodedJWT getTokenInfo(String token){
        try{
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            return verifier.verify(token);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
