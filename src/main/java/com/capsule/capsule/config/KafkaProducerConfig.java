package com.capsule.capsule.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.kafka.autoconfigure.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

@Configuration
public class KafkaProducerConfig {
   private KafkaProperties kafkaProperties;

   public KafkaProducerConfig(KafkaProperties kafkaProperties) {
      this.kafkaProperties = kafkaProperties;
   }

   @Value("${topics.convert.request.topic}")
   private String convertAudioRequestTopic;

   @Bean
   public ProducerFactory<String, String> producerFactory() {
      Map<String, Object> properties = kafkaProperties.buildProducerProperties();
      return new DefaultKafkaProducerFactory<>(properties);
   }

   @Bean
   public KafkaTemplate<String, String> kafkaTemplate() {
      return new KafkaTemplate<>(producerFactory());
   }

   @Bean
   public NewTopic convertAudioRequestTopicBuilder() {
      return TopicBuilder
              .name(convertAudioRequestTopic)
              .partitions(1)
              .replicas(1)
              .build();
   }
}
