package com.ums.server.filters;

import com.ums.server.exceptions.JwtException;
import com.ums.server.exceptions.JwtSessionExpiredException;
import com.ums.server.exceptions.JwtTokenExpiredException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Slf4j
@Component
public class FilterChainExceptionHandler extends OncePerRequestFilter {

    private final HandlerExceptionResolver resolver;

    //    This name of the qualifier bean is needed because there are another resolver bean is present in the context.
    public FilterChainExceptionHandler(@NonNull @Qualifier(value = "handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resolver.resolveException(request, response, null, e);
        }
    }
}
