package com.capsule.capsule.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.capsule.capsule.dtos.request.CreateUserRequest;
import com.capsule.capsule.dtos.response.UserResponse;
import com.capsule.capsule.entities.User;
import com.capsule.capsule.repository.UserRepository;

@Service
public class UserService {

   private final UserRepository repository;

   public UserService(UserRepository repository) {
      this.repository = repository;
   }

   public UserResponse createUser(CreateUserRequest request) {
      try {
         User user = new User();

         user.setEmail(request.email());
         user.setName(request.name());
         user.setPassword(request.password());

         repository.save(user);

         return new UserResponse(
               user.getName(),
               user.getEmail());

      } catch (Exception e) {
         throw new RuntimeException("Erro ao criar usuário.", e);
      }
   }

   public User listUserById(Long id) {
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
               .map(user -> new UserResponse(
                     user.getName(),
                     user.getEmail()))
               .toList();
      } catch (Exception e) {
         throw new RuntimeException("Erro ao listar usuários.", e);
      }
   }

   public UserResponse deleteUserByID(Long id) {
      try {
         User user = repository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

         UserResponse userDTO = new UserResponse(
               user.getName(),
               user.getEmail());

         repository.delete(user);
         return userDTO;
      } catch (Exception e) {
         throw new RuntimeException("Erro ao deletar usuário.", e);
      }
   }
}
