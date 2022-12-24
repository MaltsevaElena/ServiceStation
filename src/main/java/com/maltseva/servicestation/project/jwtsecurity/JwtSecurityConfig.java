package com.maltseva.servicestation.project.jwtsecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true)
public class JwtSecurityConfig implements WebMvcConfigurer {

    private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            /*    "/v2/api-docs",
                "/swagger-resources",
                "/swagger-resources/**",
                "/configuration/ui",
                "/configuration/security",
                "/swagger-ui.html",
                "/swagger-ui.html/**",
                "/webjars/**",*/
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/css/**",
            "/img/**",
            "/js/**",
            "/encode/*",
            // other public endpoints of your API may be appended to this array
            "/user/auth",
            "/user/remember-password",
            "/user/change-password/{userId}",
            "/user/add"

    };
    private final JwtTokenFilter jwtTokenFilter;

    public JwtSecurityConfig(JwtTokenFilter jwtTokenFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
        // Inherit security context in async function calls:
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChainJwt(HttpSecurity http) throws Exception {
        http.cors()
                .and().csrf().disable()// для доступа к серверу находящемся не на том же хосте
                //включаем базовую авторизацию
                .httpBasic()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .and()
                .exceptionHandling()
                // точка доступа для аутентификации
                .authenticationEntryPoint((request, response, authException) -> {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                            authException.getMessage());
                })
                .and()
                .authorizeRequests()
                //Доступ только для авторизованных пользователей
                //Доступ для пользователя
                .antMatchers("/user/updateUser").hasRole("USER")
                .antMatchers("/user/delete").hasRole("USER")
                .antMatchers("/user/getCarsUserById").hasRole("USER")
                .antMatchers("/user/getUserOwnerCarDTO").hasRole("USER")
                .antMatchers("/car/**").hasRole("USER")
                .antMatchers("/serviceBook/**").hasAnyRole("USER", "EMPLOYEE")
                //Доступ для логиста
                .antMatchers("/serviceStation/get").hasAnyRole("LOGICT", "EMPLOYEE")
                .antMatchers("/serviceStation/getWarehousesByServiceStationId").hasRole("LOGICT")
                .antMatchers("/warehouse/get").hasAnyRole("LOGIST", "EMPLOYEE")
                .antMatchers("/sparePart/**").hasAnyRole("LOGIST", "EMPLOYEE", "DIRECTOR")
                //Доступ для сотрудника
                .antMatchers("/car/**").hasRole("EMPLOYEE")
                .antMatchers("/diagnosticSheet/**").hasRole("EMPLOYEE")
                .antMatchers("/service/get").hasRole("EMPLOYEE")
                .antMatchers("/service/listAllServiceByServiceStationId").hasRole("EMPLOYEE")
                .antMatchers("/tariff/get").hasRole("EMPLOYEE")
                //Доступ для директора
                .antMatchers("/tariff/**").hasRole("DIRECTOR")
                .antMatchers("/position/**").hasRole("DIRECTOR")
                .antMatchers("/service/**").hasRole("DIRECTOR")
                .antMatchers("/serviceStation/**").hasRole("DIRECTOR")
                .antMatchers("/warehouse/**").hasRole("DIRECTOR")
                .antMatchers("/user/**").hasRole("DIRECTOR")
                //.antMatchers("/cars/**").hasAuthority("ROLE_USER")
                //Доступ для администратора
                .antMatchers("/**").hasRole("ADMIN")
                .anyRequest().permitAll()
                .and()
                //JWT Token VALID or NOT
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}