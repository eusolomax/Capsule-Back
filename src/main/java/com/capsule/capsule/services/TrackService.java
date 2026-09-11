package com.capsule.capsule.services;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.capsule.capsule.enums.ProcessingStatus;
import com.capsule.capsule.events.CheckStorageFolder;
import com.mpatric.mp3agic.Mp3File;
import org.springframework.stereotype.Service;

import com.capsule.capsule.dtos.request.CreateTrackRequest;
import com.capsule.capsule.dtos.response.TrackResponse;
import com.capsule.capsule.entities.Track;
import com.capsule.capsule.entities.User;
import com.capsule.capsule.mapper.TrackMapper;
import com.capsule.capsule.repository.TrackRepository;
import com.capsule.capsule.enums.AudioFormat;

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

   public TrackResponse createTrack(CreateTrackRequest request, UUID userUUID) {
      User user = userService.findByUuid(userUUID);
      Track track = mapper.toEntity(request);
      Path storagePath = checkStorageFolder.path;
      AudioFormat fileFormat = Objects.equals(request.file().getContentType(), "audio/wav") ? AudioFormat.WAV : AudioFormat.MP3;

      Path filePath = Path.of(storagePath.toString() + "/" + track.getUuid().toString() + "." + fileFormat.toString().toLowerCase());

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
      track.setFormat(fileFormat);
      track.setStatus(ProcessingStatus.FINISHED);

      try {
         repository.save(track);

         return mapper.toResponse(track);
      } catch (Exception e) {
         throw new RuntimeException("Erro ao criar track.", e);
      }
   }

   private int getAudioDuration(Path path, AudioFormat fileFormat) throws Exception {
      String audioFormat = fileFormat.toString().toLowerCase();

      return switch (audioFormat) {
         case "mp3" -> {
            Mp3File mp3File = new Mp3File(path.toFile());
            yield (int) mp3File.getLengthInSeconds();
         }

         case "wav" -> {
            try (AudioInputStream audio = AudioSystem.getAudioInputStream(path.toFile())) {
               javax.sound.sampled.AudioFormat format = audio.getFormat();
               yield (int) (audio.getFrameLength() / format.getFrameRate());
            }
         }

         default -> throw new IllegalArgumentException(
                 "Formato de áudio não suportado: " + audioFormat
         );
      };
   }

   public List<TrackResponse> listAllUserTracks(UUID userUUID) {

      try {
         return repository.findByUserUuid((userUUID))
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
