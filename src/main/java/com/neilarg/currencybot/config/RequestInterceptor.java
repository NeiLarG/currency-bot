package com.neilarg.currencybot.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("[REQUEST_INFO_BEGIN]\n");
        sb.append("IP: %s\n".formatted(request.getRemoteAddr()));
        sb.append("HOST: %s\n".formatted(request.getRemoteHost()));
        sb.append("URL: %s\n".formatted(request.getRequestURL()));
        sb.append("[REQUEST_INFO_END]");
        log.info(sb.toString());
        return true;
    }
    
}
