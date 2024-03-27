package com.epam.labtaskspringcore.config;

import com.epam.labtaskspringcore.utils.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                // h2 works, no login
                http.httpBasic(Customizer.withDefaults());
                http.headers().frameOptions().disable();
                http.csrf(AbstractHttpConfigurer::disable);
                return http.build();

//        http.authorizeHttpRequests((authorize) -> authorize
//                .requestMatchers("/logout").authenticated()
//
//                .requestMatchers("/trainee").hasRole(Role.TRAINEE.toString())
//                .requestMatchers("/trainer").hasRole(Role.TRAINER.toString())
//
//                .requestMatchers("/login").permitAll()
//                .requestMatchers("/", "/home").permitAll());
//
//        http.formLogin(formLogin -> formLogin
//                .loginPage("/login")
//                .defaultSuccessUrl("/")
//                .failureUrl("/login?error=true").permitAll());
//        http.logout(logout -> logout.logoutSuccessUrl("/login?logout=true").invalidateHttpSession(true).permitAll());
//        http.httpBasic(Customizer.withDefaults());
//        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
