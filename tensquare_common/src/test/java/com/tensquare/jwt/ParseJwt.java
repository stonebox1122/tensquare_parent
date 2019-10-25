package com.tensquare.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class ParseJwt {
    public static void main(String[] args) {
        Claims claims = Jwts.parser()
                .setSigningKey("itcast")
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NjYiLCJzdWIiOiLlsI_pqawiLCJpYXQiOjE1NzE4ODM3ODMsImV4cCI6MTU3MTg4Mzg0Mywicm9sZSI6ImFkbWluIn0.Ogge9LJUX5KOW34MpLc7Yl--QQ9kLgvyPkuFmmsbQE8")
                .getBody();
        System.out.println("用户id：" + claims.getId());
        System.out.println("用户名：" + claims.getSubject());
        System.out.println("登录时间：" + claims.getIssuedAt());
        System.out.println("过期时间：" + claims.getExpiration());
        System.out.println("用户角色：" + claims.get("role"));
    }
}
