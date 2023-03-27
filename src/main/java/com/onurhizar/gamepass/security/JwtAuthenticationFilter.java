package com.onurhizar.gamepass.security;

import com.onurhizar.gamepass.model.entity.User;
import com.onurhizar.gamepass.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepository userRepository;


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        try {
            Jws<Claims> jws = jwtService.verifyAuthHeader(authorizationHeader);
            Authentication authentication = claimsToAuthentication(jws);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (ExpiredJwtException e) {
            // TODO throw specific exception
            log.warn("JWT is expired");
        }

        filterChain.doFilter(request, response);
    }

    private Authentication claimsToAuthentication(Jws<Claims> jws) {
        if (jws == null) return null;
        String email = jws.getBody().getSubject(); // email
        User user = userRepository.findByEmail(email);
        if(user == null) throw new UsernameNotFoundException(email); // even though jwt is valid, email does not exist
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }
}
