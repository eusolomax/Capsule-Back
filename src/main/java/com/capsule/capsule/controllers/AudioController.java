package com.capsule.capsule.controllers;

import com.capsule.capsule.producer.AudioConvertProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/audio")
public class AudioController {

   private final AudioConvertProducer audioConvertProducer;

   public AudioController(AudioConvertProducer audioConvertProducer) {
      this.audioConvertProducer = audioConvertProducer;
   }

   @GetMapping("/convert")
   public ResponseEntity<?> convertAudio() {
      return ResponseEntity.ok(audioConvertProducer.sendMessage("audio da request"));
   }
}
