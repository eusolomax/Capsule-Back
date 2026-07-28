package com.capsule.capsule.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.capsule.capsule.dtos.request.CreateUserRequest;
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

   public UserResponse createUser(CreateUserRequest request) {
      try {
         User user = mapper.toEntity(request);
         repository.save(user);

         return mapper.toResponse(user);
      } catch (Exception e) {
         throw new RuntimeException("Erro ao criar usuário.", e);
      }
   }

   public User findById(Long id) {
      try {
         return repository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
      } catch (Exception e) {
         throw new RuntimeException("Erro ao listar usuário.", e);
      }
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
      try {
         User user = repository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
         repository.delete(user);
         
         return mapper.toResponse(user);
      } catch (Exception e) {
         throw new RuntimeException("Erro ao deletar usuário.", e);
      }
   }
}
