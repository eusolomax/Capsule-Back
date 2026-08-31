package com.capsule.capsule.services;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.capsule.capsule.events.CheckStorageFolder;
import com.mpatric.mp3agic.Mp3File;
import org.springframework.stereotype.Service;

import com.capsule.capsule.dtos.request.CreateTrackRequest;
import com.capsule.capsule.dtos.response.TrackResponse;
import com.capsule.capsule.entities.Track;
import com.capsule.capsule.entities.User;
import com.capsule.capsule.mapper.TrackMapper;
import com.capsule.capsule.repository.TrackRepository;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

@Service
public class TrackService {

   private final UserService userService;
   private final TrackRepository repository;
   private final TrackMapper mapper;
   private final CheckStorageFolder checkStorageFolder;

   public TrackService(TrackRepository repository, UserService userService, TrackMapper mapper, CheckStorageFolder checkStorageFolder) {
      this.repository = repository;
      this.userService = userService;
      this.mapper = mapper;
      this.checkStorageFolder = checkStorageFolder;
   }

   public TrackResponse createTrack(CreateTrackRequest request, String userUUID) {
      User user = userService.findByUuid(userUUID);
      Track track = mapper.toEntity(request);
      Path storagePath = checkStorageFolder.path;
      String fileFormat = Objects.equals(request.file().getContentType(), "audio/wav") ? "wav" : "mp3";

      Path filePath = Path.of(storagePath.toString() + "/" + track.getUuid().toString() + "." + fileFormat);

      try {
         request.file().transferTo(filePath);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }

      track.setFilePath(filePath.toString());

      try {
         track.setDurationSeconds(getAudioDuration(filePath, fileFormat));
      } catch (Exception e) {
         throw new RuntimeException(e);
      }

      track.setUser(user);

      try {
         repository.save(track);

         return mapper.toResponse(track);
      } catch (Exception e) {
         throw new RuntimeException("Erro ao criar track.", e);
      }
   }

   private int getAudioDuration(Path path, String fileFormat) throws Exception {
      return switch (fileFormat.toLowerCase()) {
         case "mp3" -> {
            Mp3File mp3File = new Mp3File(path.toFile());
            yield (int) mp3File.getLengthInSeconds();
         }

         case "wav" -> {
            try (AudioInputStream audio = AudioSystem.getAudioInputStream(path.toFile())) {
               AudioFormat format = audio.getFormat();
               yield (int) (audio.getFrameLength() / format.getFrameRate());
            }
         }

         default -> throw new IllegalArgumentException(
                 "Formato de áudio não suportado: " + fileFormat
         );
      };
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
         Track trackFound = repository.findByUuid(trackUUID).orElseThrow(() -> new RuntimeException("Track não encontrada."));
         
         TrackResponse track = mapper.toResponse(trackFound);

         repository.delete(trackFound);

         return track;
      } catch (Exception e) {
         throw new RuntimeException("Erro ao deletar track.", e);
      }
   }
}
