package com.capsule.capsule.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.capsule.capsule.dtos.request.CreateTrackRequest;
import com.capsule.capsule.dtos.response.TrackResponse;
import com.capsule.capsule.entities.Track;
import com.capsule.capsule.entities.User;
import com.capsule.capsule.mapper.TrackMapper;
import com.capsule.capsule.repository.TrackRepository;

@Service
public class TrackService {

   private final UserService userService;
   private final TrackRepository repository;
   private final TrackMapper mapper;

   public TrackService(TrackRepository repository, UserService userService, TrackMapper mapper) {
      this.repository = repository;
      this.userService = userService;
      this.mapper = mapper;
   }

   public TrackResponse createTrack(CreateTrackRequest request, String userUUID) {
      User user = userService.findByUuid(userUUID);

      Track track = mapper.toEntity(request);

      try {
         track.setFilePath("dir/music.mp3");
         track.setDurationSeconds(0);
         track.setUser(user);

         repository.save(track);

         return mapper.toResponse(track);
      } catch (Exception e) {
         throw new RuntimeException("Erro ao criar track.", e);
      }
   }

   
   public List<TrackResponse> listAllUserTracks(String userUUID) {
      
      try {
         return repository.findByUserUuid(UUID.fromString(userUUID))
               .stream()
               .map(mapper::toResponse)
               .toList();
      } catch (Exception e) {
         throw new RuntimeException("Erro ao listar tracks do usuário.", e);
      }
   }

   public TrackResponse deleteTrack(UUID trackUUID) {
      try {
         Track trackFound = repository.findByUuid(trackUUID).orElseThrow(() -> new RuntimeException("Track não encontrada."));;
         TrackResponse track = mapper.toResponse(trackFound);
         
         repository.delete(trackFound);
         
         return track;
      } catch (Exception e) {
         throw new RuntimeException("Erro ao deletar track.", e);
      }
   }
}
