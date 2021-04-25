package com.lx.yeb.utils;

import io.jsonwebtoken.*;
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
    private static final long   EXPIRATION = 1 * 1000 * 60;

    /**
     * fetch 创建token
     *
     * @param userId, username
     * @return java.lang.String
     * @author lipan
     * @date 2021/3/17 15:43
     */
    public static String createToken(Integer userId, String username){
        Map<String, Object> map = new HashMap<>(4);
        map.put("userid", userId);
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
     * @author lipan
     * @date 2021/4/25 21:34
     * @param token
     * @return java.lang.String
     */

    public static String getUsernameByToken(String token){
        try{
            Claims body = Jwts.parser()
                              .setSigningKey(SECRET.getBytes(StandardCharsets.UTF_8))
                              .parseClaimsJws(token)
                              .getBody();
            Integer userid   = Integer.parseInt(body.get("userid").toString());
            String  username = body.get("username").toString();
            return username;
        }catch(Exception e){
            log.error("token解析失败", e);
            return "";
        }
    }

    /**
     * fetch 获取Token信息
     *
     * @param token
     * @return com.lx.yeb.vo.UserTokenVO
     * @author lipan
     * @date 2021/3/18 16:15
     */
    public static ResultCodeEnum getTokenInfo(String token){
        try{
            Claims body = Jwts.parser()
                              .setSigningKey(SECRET.getBytes(StandardCharsets.UTF_8))
                              .parseClaimsJws(token)
                              .getBody();

            Integer userid     = Integer.parseInt(body.get("userid").toString());
            String  username   = body.get("username").toString();
            Date    expiration = body.getExpiration();
            return ResultCodeEnum.SUCCESS;
        }catch(ExpiredJwtException e){ // token已过期
            log.error("token已过期", e);
            return ResultCodeEnum.TOKEN_HAS_EXPIRED;
        }catch(UnsupportedJwtException e){
            log.error("token解析失败4", e);
            return ResultCodeEnum.TOKEN_ERROR;
        }catch(MalformedJwtException e){
            log.error("token解析失败1", e);
            return ResultCodeEnum.TOKEN_ERROR;
        }catch(SignatureException e){
            log.error("token解析失败2", e);
            return ResultCodeEnum.TOKEN_ERROR;
        }catch(IllegalArgumentException e){
            log.error("token解析失败3", e);
            return ResultCodeEnum.TOKEN_ERROR;
        }catch(Exception e){
            log.error("token解析失败", e);
            return ResultCodeEnum.TOKEN_ERROR;
        }
    }
}
