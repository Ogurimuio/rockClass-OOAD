package com.example.rockclass.config;

import com.example.rockclass.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtService jwtService;

    private static final String ROLE_STUDENT = "ROLE_STUDENT";
    private static final String ROLE_TEACHER = "ROLE_TEACHER";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String tokenHeader = "Bearer ";

        if (authHeader != null && authHeader.startsWith(tokenHeader)) {
            String authToken = authHeader.substring(tokenHeader.length());
            JwtPayload jwtPayload = jwtService.verifyJwt(authToken);
            if (jwtPayload != null && SecurityContextHolder.getContext().getAuthentication() == null
                    && jwtPayload.getExp() > System.currentTimeMillis()) {
                User user = userDetailsService.loadUserByUsername(jwtPayload.getAccount());
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.getAccount(), user.getPassword(), getAuthorities(user.getRole()));;

                authentication.setDetails(user);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                request.setAttribute("jwtPayload",jwtPayload);
            }
        }
        chain.doFilter(request, response);

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
