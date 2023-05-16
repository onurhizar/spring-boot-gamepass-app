package com.onurhizar.gamepass.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class FilterChainConfig {


    /**
     *
     * @Bean
     *     SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
     *         http
     *             .csrf().disable()
     *             .authorizeHttpRequests()
     *             .antMatchers("/login").permitAll()
     *             .antMatchers("/register").permitAll()
     *             .antMatchers("/swagger-ui/index.html").permitAll()  // OpenAPI
     *             .antMatchers("/v3/api-docs").permitAll()            // OpenAPI
     *             .antMatchers("/user/admin-or-self-test/**") // FOR TESTING PURPOSES, TODO : remove later
     *                 .hasAnyAuthority("ADMIN", "SELF")
     *             .antMatchers(HttpMethod.DELETE, "/user/**").hasAuthority("ADMIN")
     *             .antMatchers(HttpMethod.POST, "/category/**").hasAuthority("ADMIN")
     *             .antMatchers(HttpMethod.PUT, "/category/**").hasAuthority("ADMIN")
     *             .antMatchers(HttpMethod.DELETE, "/category/**").hasAuthority("ADMIN")
     *             .anyRequest().permitAll() //.authenticated()
     *             .and()
     *             .sessionManagement()
     *             .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
     *             .and()
     *             .authenticationProvider(authenticationProvider)
     *             .addFilterBefore(selfFilter, UsernamePasswordAuthenticationFilter.class)
     *             .addFilterBefore(jwtAuthFilter, SelfFilter.class);
     *
     *         return http.build();
     *     }
     */

}