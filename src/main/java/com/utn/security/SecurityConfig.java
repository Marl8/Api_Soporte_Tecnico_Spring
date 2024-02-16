package com.utn.security;

import com.utn.security.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    AuthenticationProvider provider;

    JwtAuthenticationFilter authenticationFilter;

    public SecurityConfig(AuthenticationProvider provider, JwtAuthenticationFilter authenticationFilter) {
        this.provider = provider;
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(provider)
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/error").permitAll();
                    auth.requestMatchers("/v1/api/login").permitAll();
                    /*auth.requestMatchers(
                            "/api/**",
                            "/swagger-ui/**",
                            "/swagger-ui.html",
                            "/swagger-resources/**",
                            "/v3/api-docs/**",
                            "/configuration/**").permitAll();*/
                    auth.requestMatchers("/v1/api/incidente/guardar").hasAnyRole("USER", "ADMIN");
                    auth.requestMatchers("/v1/api/cliente/guardar").hasAnyRole("USER", "ADMIN");
                    auth.requestMatchers("/v1/api/operador/asignarHoras/{idIncidente}/{horas}").hasAnyRole("OPERADOR", "ADMIN");
                    auth.requestMatchers("/v1/api/operador//asignarTecnico/{idIncidente}").hasAnyRole("OPERADOR", "ADMIN");
                    auth.anyRequest().hasRole("ADMIN");
                })
                .build();
    }
}
