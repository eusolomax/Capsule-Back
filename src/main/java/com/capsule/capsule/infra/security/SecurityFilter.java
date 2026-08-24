package com.capsule.capsule.infra.security;

import java.io.IOException;
import java.util.UUID;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.capsule.capsule.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

   private final UserRepository userRepository;
   private final TokenService tokenService;

   public SecurityFilter(TokenService tokenService, UserRepository userRepository) {
      this.tokenService = tokenService;
      this.userRepository = userRepository;
   }

   @Override
   protected void doFilterInternal(
           HttpServletRequest request,
           HttpServletResponse response,
           FilterChain filterChain)
           throws ServletException, IOException {

      var token = recoverToken(request);

      if (token != null) {
         String uuid;

         try {
            uuid = tokenService.validateToken(token);
         } catch (JWTVerificationException e) {
            throw new RuntimeException(e);
         }

         UserDetails user = userRepository.findByUuid(UUID.fromString(uuid)).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));

         var authentication = new UsernamePasswordAuthenticationToken(
                 uuid,
                 null,
                 user.getAuthorities());

         SecurityContextHolder
                 .getContext()
                 .setAuthentication(authentication);
      }

      filterChain.doFilter(request, response);

   }

   private String recoverToken(HttpServletRequest request) {
      var authHeader = request.getHeader("Authorization");

      if (authHeader == null)
         return null;
      return authHeader.replace("Bearer ", "");
   }
}
