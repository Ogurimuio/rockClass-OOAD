package com.example.rockclass.config;

import com.example.rockclass.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private static final String ROLE_STUDENT = "ROLE_STUDENT";
    private static final String ROLE_TEACHER = "ROLE_TEACHER";

    @Autowired
    @Qualifier("customUserDetailsService")
    private UserDetailsService userDetailsService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        User user = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken auth;

        if (comparePassword(authentication.getCredentials().toString(), user.getPassword())) {
            auth = new UsernamePasswordAuthenticationToken(user, null, getAuthorities(user.getRole()));
            auth.setDetails(user);
        } else {
            throw new BadCredentialsException("");
        }
        return auth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        boolean a = authentication.equals(
                UsernamePasswordAuthenticationToken.class);
        return a;
    }

    private boolean comparePassword(String input, String trusted) {
        return trusted.equals(input);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (role.equals("student")) {
            authorities.add(new SimpleGrantedAuthority(ROLE_STUDENT));
        } else if (role.equals("teacher")) {
            authorities.add(new SimpleGrantedAuthority(ROLE_TEACHER));
        }
        return authorities;
    }

}
