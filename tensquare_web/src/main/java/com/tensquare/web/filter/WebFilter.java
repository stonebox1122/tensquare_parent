package com.tensquare.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class WebFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        // 得到request上下文
        RequestContext requestContext = RequestContext.getCurrentContext();
        // 得到request域
        HttpServletRequest request = requestContext.getRequest();
        // 得到头信息
        String authorization = request.getHeader("Authorization");
        // 判断是否有头信息
        if (authorization != null && !"".equals(authorization)) {
            // 把头信息继续向下传
            requestContext.addZuulRequestHeader("Authorization", authorization);
        }
        return null;
    }
}
