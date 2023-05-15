package com.onurhizar.gamepass.security;

import com.onurhizar.gamepass.model.entity.User;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Component
@RequiredArgsConstructor
/**
 * This filter checks the Authentication object from SecurityContext and takes user id from it.
 * Then compares requested URL path if contains user's id.
 * If there is a match, it appends "SELF" authority to current user.
 *
 * Example: /user/5b8a3d25-2b7a-4683-89ed-ac0e42cdc879
 * If the authenticated user has the same ID, it can reach the resource,
 * so that we can implement "ADMIN OR SELF" logic on some routes.
 *
 * Note: this filter needs to be improved since it only checks url "contains" the id.
 * This is so generic form of controlling,
 * instead, use parsing and checking if it comes after "user" path.
 */
public class SelfFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            String userId = user.getId();

            if (request.getRequestURI().contains(userId)){
                List<GrantedAuthority> updatedAuthorities = new LinkedList<>(authentication.getAuthorities());
                updatedAuthorities.add(new SimpleGrantedAuthority("SELF")); // TODO : use enum later
                Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
                        authentication.getPrincipal(),
                        authentication.getCredentials(),
                        updatedAuthorities
                );
                SecurityContextHolder.getContext().setAuthentication(newAuthentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}