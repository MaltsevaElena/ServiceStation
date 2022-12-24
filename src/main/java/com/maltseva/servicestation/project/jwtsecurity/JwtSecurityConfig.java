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
           "/user/**"
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
                //Доступ для администратора для всего
                //.antMatchers("/api/**").hasRole("ADMIN")
                //Доступ для логиста
                /*.antMatchers("/api/warehouse/getWarehouse").hasRole("LOGIST")
                .antMatchers("/api/sparePart/**").hasRole("LOGIST")
*/

                //.antMatchers("/Cars/**").hasRole("user")
                //.antMatchers("/Tariffs/**").hasRole("user")
                .antMatchers("/**").hasRole("DIRECTOR")

                //.antMatchers("/cars/**").hasAuthority("ROLE_USER")
                //.antMatchers("/cars/**").hasAneRole("USER", "ADMIN",...)
                .anyRequest().permitAll()
                .and()
                //JWT Token VALID or NOT
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}