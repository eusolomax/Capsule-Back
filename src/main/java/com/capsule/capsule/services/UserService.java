package com.capsule.capsule.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.capsule.capsule.entities.User;
import com.capsule.capsule.repository.UserRepository;

@Service
public class UserService {

   private final UserRepository repository;

   public UserService(UserRepository repository) {
      this.repository = repository;
   }

   public User createUser(User user) {
      try {
         return repository.save(user);
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

   public List<User> listAll() {
      try {
         return repository.findAll();
      } catch (Exception e) {
         throw new RuntimeException("Erro ao listar usuários.", e);
      }
   }

   public User deleteUserByID(Long id) {
      try {
         User user = repository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

         repository.delete(user);
         return user;
      } catch (Exception e) {
         throw new RuntimeException("Erro ao deletar usuário.", e);
      }
   }
}
