package com.capsule.capsule.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

   private final SecurityFilter securityFilter;

   public SecurityConfigurations(SecurityFilter securityFilter) {
      this.securityFilter = securityFilter;
   }

   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
      return httpSecurity
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(request -> {
               CorsConfiguration config = new CorsConfiguration();

               config.setAllowedOrigins(
                     List.of("*"));

               config.setAllowedMethods(
                     List.of("*"));

               config.setAllowedHeaders(
                     List.of("*"));

               return config;
            }))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> authorize
                  .requestMatchers("/auth/**").permitAll()
                  .requestMatchers("/**").hasRole("USER")
                  .anyRequest().authenticated())
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
   }

   @Bean
   public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) {
      return authenticationConfiguration.getAuthenticationManager();
   }

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }
}
