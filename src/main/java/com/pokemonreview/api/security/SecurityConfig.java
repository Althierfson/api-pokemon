package com.pokemonreview.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        // http
        //         .csrf().disable()
        //         .exceptionHandling()
        //         .authenticationEntryPoint(authEntryPoint)
        //         .and()
        //         .sessionManagement()
        //         .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        //         .and()
        //         .authorizeRequests()
        //         .antMatchers("/api/auth/**").permitAll()
        //         .antMatchers("/swagger-ui/").permitAll()
        //         .anyRequest().authenticated()
        //         .and()
        //         .httpBasic();
        http.csrf(csrf -> csrf.disable())  // Desativa CSRF de maneira mais explícita no Spring Security 6
        .exceptionHandling(exception -> 
            exception.authenticationEntryPoint(authEntryPoint))
        .sessionManagement(session -> 
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(authorize -> 
            authorize
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/swagger-ui/").permitAll()
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
