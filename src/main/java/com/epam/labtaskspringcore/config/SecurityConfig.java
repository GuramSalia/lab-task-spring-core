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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // h2 works, no login
        http.cors(Customizer.withDefaults());
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
        //        http.logout(logout -> logout.logoutSuccessUrl("/login?logout=true").invalidateHttpSession(true)
        //        .permitAll());
        //        http.httpBasic(Customizer.withDefaults());
        //        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSourceForTrainees() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://example1.com"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/trainee/**", configuration);
        return source;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSourceForTrainers() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://example2.com"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/trainer/**", configuration);
        return source;
    }
}
