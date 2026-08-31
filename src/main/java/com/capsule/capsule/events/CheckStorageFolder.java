package com.capsule.capsule.events;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class CheckStorageFolder {
   public Path path = Path.of(System.getProperty("user.home"), ".capsule");

   @EventListener(ApplicationReadyEvent.class)
   public void checkStorage() {
      try {
         Files.createDirectories(this.path);
      } catch (IOException e) {
         System.err.println("Failed to create directory: " + e.getMessage());
      }
   }
}
