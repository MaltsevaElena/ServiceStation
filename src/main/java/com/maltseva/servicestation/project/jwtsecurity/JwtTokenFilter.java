package com.maltseva.servicestation.project.jwtsecurity;

import com.maltseva.servicestation.project.service.userdetails.CustomUserDetailsService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailsService authenticationService;

    @Getter
    private String token;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        //Обнуление токена перед фильтрацией от предыдущей авторизации
        token = null;
        //получение заголовка авторизации и подтверждение
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        // проверяет что в строке заголовка присутствует авторизация
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        //получаем JWT токен
        //Authorization: Bearer 123jhvzdfksahegfk1gh3vkhasvdkj#$@123mnbasljhgdasjhedlvuy2fe
        token = header.split(" ")[1].trim();
        //Получить пользователя
        UserDetails userDetails;
        userDetails = authenticationService.loadUserByUsername(jwtTokenUtil.getUsernameFromToken(token));

        //Подтверждение токена
        if (!jwtTokenUtil.validateToken(token, userDetails)) {
            filterChain.doFilter(request, response);
            return;
        }

        //установка сущности пользователя на spring security context
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                null,
                userDetails.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}