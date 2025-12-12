package com.healthpal.config;

import com.healthpal.entity.HealthAlert;
import com.healthpal.repository.HealthAlertRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDateTime;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(HealthAlertRepository alertRepo) {
        return args -> {
            // Only add data if table is empty
            if (alertRepo.count() == 0) {
                HealthAlert alert1 = new HealthAlert();
                alert1.setTitle("Heatwave Warning");
                alert1.setContent("Extreme temperatures expected. Stay hydrated.");
                alert1.setSeverity("HIGH");
                alert1.setRegion("Gaza Strip");
                alert1.setPublishedAt(LocalDateTime.now());
                alert1.setActive(true);
                alertRepo.save(alert1);

                HealthAlert alert2 = new HealthAlert();
                alert2.setTitle("Vaccination Campaign");
                alert2.setContent("Polio vaccination starts tomorrow.");
                alert2.setSeverity("MEDIUM");
                alert2.setRegion("West Bank");
                alert2.setPublishedAt(LocalDateTime.now().minusDays(1));
                alert2.setActive(true);
                alertRepo.save(alert2);

                System.out.println("✅ Test Data Inserted Successfully!");
            }
        };
    }
}