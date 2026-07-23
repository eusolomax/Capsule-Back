package com.capsule.capsule.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.capsule.capsule.dtos.request.CreateTrackRequest;
import com.capsule.capsule.dtos.response.TrackResponse;
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

   public TrackResponse createTrack(CreateTrackRequest request) {
      // Pegar com token JWT
      User user = userService.listUserById(6L);

      Track track = new Track();

      try {
         track.setTrackName(request.trackName());
         track.setTrackDescription(request.trackDescription());
         track.setFilePath("dir/music.mp3");
         track.setDurationSeconds(0);
         track.setUser(user);

         repository.save(track);

         return new TrackResponse(
               track.getUuid(),
               track.getTrackName(),
               track.getTrackDescription(),
               track.getDurationSeconds(),
               track.getFilePath());
      } catch (Exception e) {
         throw new RuntimeException("Erro ao criar track.", e);
      }
   }

   public List<TrackResponse> listAllUserTracks(Long userId) {
      try {
         return repository.findByUserId(userId)
               .stream()
               .map(track -> new TrackResponse(
                     track.getUuid(),
                     track.getTrackName(),
                     track.getTrackDescription(),
                     track.getDurationSeconds(),
                     track.getFilePath()))
               .toList();
      } catch (Exception e) {
         throw new RuntimeException("Erro ao listar tracks do usuário.", e);
      }
   }

   public TrackResponse deleteTrack(UUID trackUUID) {
      try {
         Track trackFound = repository.findByUuid(trackUUID).orElseThrow(() -> new RuntimeException("Track não encontrada."));;
         
         TrackResponse track = new TrackResponse(
            trackFound.getUuid(),
            trackFound.getTrackName(),
            trackFound.getTrackDescription(),
            trackFound.getDurationSeconds(),
            trackFound.getFilePath()
         );
         
         repository.delete(trackFound);
         
         return track;
      } catch (Exception e) {
         throw new RuntimeException("Erro ao deletar track.", e);
      }
   }
}
