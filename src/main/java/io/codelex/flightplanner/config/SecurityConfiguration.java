package io.codelex.flightplanner.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/testing-api/**", "/h2-console", "/api/**")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/admin-api/**")
                .authenticated()
                .and()
                .httpBasic();
        return http.build();
    }

}