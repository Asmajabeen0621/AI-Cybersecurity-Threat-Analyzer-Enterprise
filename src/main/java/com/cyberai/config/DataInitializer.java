package com.cyberai.config;

import com.cyberai.model.*;
import com.cyberai.repository.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@Configuration
public class DataInitializer {

 @Bean
 CommandLineRunner seed(
         AppUserRepository users,
         ThreatIntelRepository intel,
         PasswordEncoder enc) {

  return args -> {

   // Initialize default users
   if (users.count() == 0) {

    AppUser admin = new AppUser();
    admin.setUsername("admin");
    admin.setPassword(enc.encode("admin123"));
    admin.setRole(Role.ADMIN);
    users.save(admin);

    AppUser analyst = new AppUser();
    analyst.setUsername("analyst");
    analyst.setPassword(enc.encode("analyst123"));
    analyst.setRole(Role.ANALYST);
    users.save(analyst);
   }

   // Initialize default threat intelligence indicators
   if (intel.count() == 0) {

    for (String[] x : new String[][] {
            {"powershell", "COMMAND", "HIGH",
                    "Local demo indicator"},

            {"ransomware", "KEYWORD", "CRITICAL",
                    "Malware keyword"},

            {"verify your account", "PHISHING", "HIGH",
                    "Phishing phrase"},

            {"185.", "IP_PREFIX", "HIGH",
                    "Demo suspicious prefix"}
    }) {

     ThreatIntel t = new ThreatIntel();

     t.setIndicator(x[0]);
     t.setIndicatorType(x[1]);
     t.setSeverity(x[2]);
     t.setDescription(x[3]);
     t.setSource("LOCAL-DATASET");

     intel.save(t);
    }
   }
  };
 }
}