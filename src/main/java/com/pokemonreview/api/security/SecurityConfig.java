package com.pokemonreview.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtAuthEntryPoint authEntryPoint;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())  // Desativa CSRF de maneira mais explícita no Spring Security 6
        .exceptionHandling(exception -> 
            exception.authenticationEntryPoint(authEntryPoint))
        .sessionManagement(session -> 
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(authorize -> 
            authorize
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                
                // pokemon
                .requestMatchers(HttpMethod.POST, "/api/pokemon").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/pokemon").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/pokemon").hasRole("ADMIN")
                // types
                .requestMatchers(HttpMethod.POST, "/api/types").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/types").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/types").hasRole("ADMIN")

                .anyRequest().authenticated())
        .httpBasic(Customizer.withDefaults()); 

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public  JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter();
    }
}
