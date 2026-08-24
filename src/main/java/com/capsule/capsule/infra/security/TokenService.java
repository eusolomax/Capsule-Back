package com.capsule.capsule.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.capsule.capsule.entities.User;

@Service
public class TokenService {
   public Algorithm algorithm;

   public TokenService(@Value("${api.security.token.secret}") String secret) {
      this.algorithm = Algorithm.HMAC256(secret);
   }

   public String generateToken(User user) {
      try {
         return JWT.create()
                 .withIssuer("capsule-api")
                 .withSubject(user.getUuid().toString())
                 .withExpiresAt(genExpirationDate())
                 .sign(this.algorithm);

      } catch (JWTCreationException e) {
         throw new RuntimeException(e);
      }
   }

   public String validateToken(String token) throws JWTVerificationException {
      try {
         return JWT.require(this.algorithm)
                 .withIssuer("capsule-api")
                 .build()
                 .verify(token)
                 .getSubject();
      } catch (JWTVerificationException e) {
         throw new JWTVerificationException("Token inválido.", e);
      }
   }

   private Instant genExpirationDate() {
      return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
   }
}
