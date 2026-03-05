package com.cos.security2.filter;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

public class MyFilter2 implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = request.getRequestURI();

        // 크롬 개발자 도구 자동 요청이나 인증 경로 무시
        if (uri.contains(".well-known") || uri.contains("com.chrome.devtools.json")) {
            servletResponse.setContentType("application/json");
            servletResponse.setCharacterEncoding("UTF-8");
            servletResponse.getWriter().write("{\"status\": \"ok\", \"message\": \"Chrome DevTools auto request ignored\"}");
            return;
        }

        if(request.getMethod().equals("POST")) {
            String headerAuth = request.getHeader("Authorization");
            System.out.println("headerAuth: " + headerAuth);

            if (Objects.equals(headerAuth, "Bia")) {
                System.out.println("필터2 실행 - URI: " + request.getRequestURI());
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                PrintWriter out = response.getWriter();
                out.println("인증 안 됨");
            }
        }
    }
}
