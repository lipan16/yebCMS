package com.lx.yeb.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
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
@Slf4j
public class JwtUtil{
    // 密钥
    private static final String SECRET     = "suokou@hunan";
    // 过期时间 30min
    private static final long   EXPIRATION = 30 * 60 * 1000;
    /**
     * fetch 创建token
     *
     * @param username
     * @return java.lang.String
     * @author lipan
     * @date 2021/3/17 15:43
     */
    public static String createToken(String username){
        Map<String, Object> map = new HashMap<>(4);
        map.put("username", username);

        long time       = System.currentTimeMillis();
        Date now        = new Date(time);
        Date expireTime = new Date(time + EXPIRATION);

        String token = Jwts.builder()
                           .setClaims(map)
                           .setIssuedAt(now)
                           .setExpiration(expireTime)
                           .signWith(SignatureAlgorithm.HS512, SECRET.getBytes(StandardCharsets.UTF_8))
                           .compact();
        log.info("token生成成功：{}", token);
        log.info("token过期时间：{}", expireTime);
        return token;
    }

    /**
     * fetch 通过token获取用户名
     *
     * @param token
     * @return java.lang.String
     * @author lipan
     * @date 2021/4/25 21:34
     */
    public static String getUsernameByToken(String token){
        try{
            Claims body = Jwts.parser()
                              .setSigningKey(SECRET.getBytes(StandardCharsets.UTF_8))
                              .parseClaimsJws(token)
                              .getBody();
            return body.get("username").toString();
        }catch(Exception e){ // token已过期
            // token已过期, token格式错误, token没有被正确构造, token签名失败, token非法参数异常
            throw e;
        }
    }
}
