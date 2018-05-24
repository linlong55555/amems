package com.eray.common.util;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenUtil {
	
	
	private static final String SECRET_KEY="adcc";

    public static String getJWTString(String username,String password){
        String jwtString="Bearer ";
        jwtString+= Jwts.builder().
                setIssuedAt(new Date()).claim("username", username).claim("password",password).
                signWith(SignatureAlgorithm.HS256,SECRET_KEY).
                compact();
        return  jwtString;
    }

    public static String getJWTMessage(String token){
        if (token == null || !token.startsWith("Bearer ")) {
            throw new RuntimeException("登录过期，请重新验证");
        }
        token = token.substring(7);
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY)
                .parseClaimsJws(token).getBody();
        String str=(String)claims.get("username");
        str+="_"+(String)claims.get("password");
        return str;
    }
    
    /**
     * 专门给机务登录加密的方法，不要随便用
     *
     * @param authUserId auth系统的用户id
     * @return
     */
    public static String getMechanismJWTString(String authUserId) {
        String jwtString = "Bearer ";
        jwtString += Jwts.builder().
                setIssuedAt(new Date()).claim("authUserId", authUserId).
                signWith(SignatureAlgorithm.HS256, SECRET_KEY).
                compact();
        return jwtString;
    }

    public static void main(String[] args) {
        String encode=getJWTString("zc","123456");
        String decode=getJWTMessage(encode);
    	System.out.println("编码后:"+encode);
    	System.out.println("解码后:"+decode);
    }
}
