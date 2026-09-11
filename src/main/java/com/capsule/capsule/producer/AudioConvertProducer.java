package com.capsule.capsule.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class AudioConvertProducer {

   private final ObjectMapper objectMapper;
   private final KafkaTemplate<String, String> kafkaTemplate;

   public AudioConvertProducer(ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate) {
      this.objectMapper = objectMapper;
      this.kafkaTemplate = kafkaTemplate;
   }

   @Value("${topics.convert.request.topic}")
   private String convertAudioRequestTopic;

   public String sendMessage(Object audio) {
      String conteudo = objectMapper.writeValueAsString(audio);
      kafkaTemplate.send(convertAudioRequestTopic, conteudo);

      return "Mensagem enviada para conversão";
   }
}
