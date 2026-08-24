package com.capsule.capsule.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.capsule.capsule.dtos.response.UserResponse;
import com.capsule.capsule.entities.User;
import com.capsule.capsule.mapper.UserMapper;
import com.capsule.capsule.repository.UserRepository;

@Service
public class UserService {

   private final UserRepository repository;
   private final UserMapper mapper;

   public UserService(UserRepository repository, UserMapper mapper) {
      this.repository = repository;
      this.mapper = mapper;
   }

   public User findById(Long id) {
      return (User) repository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
   }

   public User findByUuid(String uuid) {
      return (User) repository.findByUuid(UUID.fromString(uuid)).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
   }

   public List<UserResponse> listAll() {
      try {
         return repository.findAll()
                 .stream()
                 .map(mapper::toResponse)
                 .toList();
      } catch (Exception e) {
         throw new RuntimeException("Erro ao listar usuários.", e);
      }
   }

   public UserResponse deleteUserByID(Long id) {
      User user = repository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

      try {
         repository.delete(user);
      } catch (Exception e) {
         throw new RuntimeException("Erro ao deletar usuário.", e);
      }
      
      return mapper.toResponse(user);
   }
}
