package com.epam.labtaskspringcore.security;

import com.epam.labtaskspringcore.service.UserService;
import com.epam.labtaskspringcore.utils.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class GymAppAuthentificationProvider implements AuthenticationProvider {
    private final UserService userService;

    @Autowired
    public GymAppAuthentificationProvider(UserService userService) {this.userService = userService;}

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("\n\n in GymAppAuthentificationProvider ...");
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        Role role = userService.performAuthentication(username, password);
        return new UsernamePasswordAuthenticationToken(username, password, getGrantedAuthorities(role));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }

    private List<GrantedAuthority> getGrantedAuthorities(Role role) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.toString()));
        return grantedAuthorities;
    }
}
