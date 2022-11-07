package vttp2022batch1.finalProjectServer.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import vttp2022batch1.finalProjectServer.filters.JwtRequestFilter;


@EnableWebSecurity
public class SecurityConfig {



    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf()
            .disable()
            .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
            .authorizeRequests()
            // .antMatchers("/authenticate", "/registration", "/logout", "/css/**", "/js/**", "/img/**", "/lib/**", "/favicon.io", "/resources/**", "/static/**", "/styles/**", "/").permitAll()
            .antMatchers("/*", "/assets/**").permitAll()
            .anyRequest().authenticated()
            // .authorizeRequests().antMatchers("/authenticate", "/registration", "/logout", "/").permitAll()
            // .anyRequest().authenticated()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}


