package com.capsule.capsule.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.capsule.capsule.entities.Track;
import com.capsule.capsule.entities.User;
import com.capsule.capsule.repository.TrackRepository;

@Service
public class TrackService {

   private final UserService userService;
   private final TrackRepository repository;

   public TrackService(TrackRepository repository, UserService userService) {
      this.repository = repository;
      this.userService = userService;
   }

   public Track createTrack(Track track) {
      User user = userService.listUserById(6L);
      
      try {
         track.setDurationSeconds(82);
         track.setFilePath("dir/music.mp3");
         track.setUser(user);
         
         return repository.save(track);
      } catch (Exception e) {
         throw new RuntimeException("Erro ao criar track.", e);
      }
   }

   public List<Track> listAllUserTracks(Long userId) {
      try {
         return repository.findByUserId(userId);
      } catch (Exception e) {
         throw new RuntimeException("Erro ao listar tracks do usuário.", e);
      }
   }

   // public User deleteUserByID(Long id) {
   //    try {
   //       User user = repository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
         
   //       repository.delete(user);
   //       return user;
   //    } catch (Exception e) {
   //       throw new RuntimeException("Erro ao deletar usuário.", e);
   //    }
   // }
}
