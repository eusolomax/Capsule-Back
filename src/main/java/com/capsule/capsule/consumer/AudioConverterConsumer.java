package com.capsule.capsule.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AudioConverterConsumer {

   @KafkaListener(
           topics = "${topics.convert.request.topic}",
           groupId = "audio-convert-consumer-1"
   )
   public void consumer() {
      System.out.println("---- Mensagem recebida! ----");
   }

}