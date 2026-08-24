package com.capsule.capsule.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.capsule.capsule.dtos.request.CreateUserRequest;
import com.capsule.capsule.dtos.response.UserResponse;
import com.capsule.capsule.entities.User;
import com.capsule.capsule.mapper.UserMapper;
import com.capsule.capsule.repository.UserRepository;

@Service
public class AuthorizationService implements UserDetailsService {

   private final UserRepository repository;
   private final UserMapper mapper;

   AuthorizationService(UserRepository repository, UserMapper mapper) {
      this.repository = repository;
      this.mapper = mapper;
   }

   @Override
   public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      return repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));
   }

   public UserResponse createUser(CreateUserRequest request) {
      try {
         User user = mapper.toEntity(request);
         user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

         repository.save(user);

         return mapper.toResponse(user);
      } catch (Exception e) {
         throw new RuntimeException("Erro ao criar usuário.", e);
      }
   }
}
