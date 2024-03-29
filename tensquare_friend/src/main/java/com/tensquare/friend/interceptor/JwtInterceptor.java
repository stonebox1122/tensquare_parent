package com.tensquare.friend.interceptor;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("经过了拦截器");
        // 无论如何都放行，具体能不能操作还是在具体的操作中去判断
        // 拦截器只是负责把请求头中包含token的令牌进行一个解析验证
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && !"".equals(authHeader)) {
            if (authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    String roles = (String) claims.get("roles");
                    if (roles != null && "admin".equals(roles)) {
                        request.setAttribute("claims_admin", claims);
                    }
                    if (roles != null && "user".equals(roles)) {
                        request.setAttribute("claims_user", claims);
                    }
                } catch (Exception e) {
                    throw new RuntimeException("令牌不正确");
                }
            }
        }
        return true;
    }
}
