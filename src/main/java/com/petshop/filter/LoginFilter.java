package com.petshop.filter;


import com.alibaba.fastjson.JSON;
import com.petshop.common.BaseContext;
import com.petshop.common.Result;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;

@WebFilter(filterName = "loginFilter", urlPatterns = "/*")
@Component
public class LoginFilter implements Filter {

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String reuqestURL = request.getRequestURI();

        String[] urls = new String[] {
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login",
                "/webjars/**",
        };
        boolean check = check(urls, reuqestURL);
        if (check) {
            filterChain.doFilter(request, response);
            return ;
        }

        // 已经登录
        if (request.getSession().getAttribute("employee") != null) {
            Long empID = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empID);
            filterChain.doFilter(request, response);
            return ;
        }

        if (request.getSession().getAttribute("user") != null) {
            Long userID = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userID);

            filterChain.doFilter(request, response);

            return ;
        }
        response.getWriter().write(JSON.toJSONString(Result.error("NOTLOGIN")));
    }
    public boolean check(String[] urls, String requersURL) {
        for (String url : urls) {
            boolean match = antPathMatcher.match(url, requersURL);
            if (match) {
                return true;
            }
        }
        return false;
    }

}
