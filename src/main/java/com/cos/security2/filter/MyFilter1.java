package com.cos.security2.filter;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public class MyFilter1 implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String uri = request.getRequestURI();

        // 크롬 개발자 도구 자동 요청이나 인증 경로 무시
        if (uri.contains(".well-known") || uri.contains("com.chrome.devtools.json")) {
            servletResponse.setContentType("application/json");
            servletResponse.setCharacterEncoding("UTF-8");
            servletResponse.getWriter().write("{\"status\": \"ok\", \"message\": \"Chrome DevTools auto request ignored\"}");
            return;
        }

        System.out.println("필터1 실행 - URI: " + request.getRequestURI());
        filterChain.doFilter(servletRequest, servletResponse);

    }
}
