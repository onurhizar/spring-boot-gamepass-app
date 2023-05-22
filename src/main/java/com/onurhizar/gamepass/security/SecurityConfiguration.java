package com.onurhizar.gamepass.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final SelfFilter selfFilter;
    private final AuthenticationProvider authenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/login", "/register").permitAll()
                .antMatchers("/verify/**", "/recover/**").permitAll()
                .antMatchers("/swagger-ui/index.html", "/v3/api-docs").permitAll()  // Swagger OpenAPI

                // ADMIN OR SELF ROUTES RESTRICTIONS
                .antMatchers(HttpMethod.GET, "/user/**", "/user/**/invoice").hasAnyAuthority("ADMIN", "SELF")
                .antMatchers(HttpMethod.PUT, "/user/**").hasAnyAuthority("ADMIN", "SELF")


                // SELF ONLY ROUTES RESTRICTIONS
                // TODO /user/**/game route is not implemented yet
                .antMatchers(HttpMethod.GET, "/user/**/game", "/user/**/game/**",
                        "/user/**/category", "/user/**/category/**").hasAuthority("SELF")
                .antMatchers(HttpMethod.POST, "/user/**/subscribe/**","/user/**/game/**/favorite",
                        "/user/**/game/**/unfavorite", "/user/**/category/**/follow",
                        "/user/**/category/**/unfollow", "/invoice/**/pay").hasAuthority("SELF")


                // ADMIN ONLY ROUTES RESTRICTIONS
                .antMatchers(HttpMethod.GET, "/user","/invoice","/invoice/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/subscription","/category/**","/game").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/subscription/**","/category/**","/game/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/subscription/**","/category/**","/game/**", "/user/**")
                .hasAuthority("ADMIN")

                .anyRequest().permitAll() //.authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(selfFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthFilter, SelfFilter.class);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        corsConfiguration.addAllowedMethod("PUT");
        corsConfiguration.addAllowedMethod("DELETE");
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

}
