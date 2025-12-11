package com.healthpal.service.impl;

import com.healthpal.dto.*;
import com.healthpal.entity.*;
import com.healthpal.repository.*;
import com.healthpal.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;   // ← هاد اللي كان ناقص!

@Service
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService {

    private final HealthGuideRepository healthGuideRepo;
    private final WebinarRepository webinarRepo;
    private final WorkshopRepository workshopRepo;
    private final MedicalMissionRepository medicalMissionRepo;
    private final NotificationRepository notificationRepo;

    private final ModelMapper mapper = new ModelMapper();

    // Health Guides
    @Override
    public HealthGuide createHealthGuide(HealthGuide guide) {
        guide.setCreated_at(LocalDateTime.now());
        return healthGuideRepo.save(guide);
    }

    @Override
    public List<HealthGuide> getAllHealthGuides() {
        return healthGuideRepo.findAll();
    }

    @Override
    public HealthGuide getHealthGuideById(Integer id) {
        return healthGuideRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Health guide not found"));
    }

    @Override
    public HealthGuide updateHealthGuide(Integer id, HealthGuide guide) {
        HealthGuide existing = getHealthGuideById(id);
        mapper.map(guide, existing);
        existing.setGuide_id(id);
        return healthGuideRepo.save(existing);
    }

    @Override
    public void deleteHealthGuide(Integer id) {
        if (!healthGuideRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Health guide not found");
        }
        healthGuideRepo.deleteById(id);
    }

    // Webinars
    @Override
    public Webinar createWebinar(WebinarCreateDTO dto) {
        Webinar webinar = Webinar.builder()
                .title(dto.getTitle())
                .description("") // ← خليناها فاضية مؤقتًا عشان ما يطلع إرور
                .link(dto.getLink())
                .scheduled_at(dto.getDate().atTime(dto.getTime()))
                .host(dto.getSpeaker())
                .createdBy(dto.getCreatedBy())
                .created_at(LocalDateTime.now())
                .build();
        return webinarRepo.save(webinar);
    }

    @Override
    public List<Webinar> getAllWebinars() {
        return webinarRepo.findAll();
    }

    @Override
    public Webinar getWebinarById(Integer id) {
        return webinarRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Webinar not found"));
    }

    // Workshops
    @Override
    public Workshop createWorkshop(WorkshopCreateDTO dto) {
        Workshop workshop = Workshop.builder()
                .title(dto.getTitle())
                .description("") // ← خليناها فاضية مؤقتًا
                .location(dto.getRegion())
                .event_date(dto.getDate())
                .createdBy(dto.getCreatedBy())
                .created_at(LocalDateTime.now())
                .build();
        return workshopRepo.save(workshop);
    }

    @Override
    public List<Workshop> getAllWorkshops() {
        return workshopRepo.findAll();
    }

    @Override
    public Workshop getWorkshopById(Integer id) {
        return workshopRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Workshop not found"));
    }

    // Medical Missions
    @Override
    public MedicalMission createMedicalMission(MedicalMission mission) {
        mission.setCreated_at(LocalDateTime.now());
        return medicalMissionRepo.save(mission);
    }

    @Override
    public List<MedicalMission> getAllMedicalMissions() {
        return medicalMissionRepo.findAll();
    }

    @Override
    public MedicalMission getMedicalMissionById(Integer id) {
        return medicalMissionRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Medical mission not found"));
    }

    // Notifications
    @Override
    public NotificationEntity createNotification(NotificationEntity notification) {
        notification.setCreated_at(LocalDateTime.now());
        return notificationRepo.save(notification);
    }
}