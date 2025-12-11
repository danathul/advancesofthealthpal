package com.healthpal.service;

import com.healthpal.dto.*;
import com.healthpal.entity.*;
import java.util.List;

public interface ContentService {

    // Health Guides
    HealthGuide createHealthGuide(HealthGuide guide);
    List<HealthGuide> getAllHealthGuides();
    HealthGuide getHealthGuideById(Integer id);
    HealthGuide updateHealthGuide(Integer id, HealthGuide guide);
    void deleteHealthGuide(Integer id);

    // Webinars
    Webinar createWebinar(WebinarCreateDTO dto);
    List<Webinar> getAllWebinars();
    Webinar getWebinarById(Integer id);

    // Workshops
    Workshop createWorkshop(WorkshopCreateDTO dto);
    List<Workshop> getAllWorkshops();
    Workshop getWorkshopById(Integer id);

    // Medical Missions
    MedicalMission createMedicalMission(MedicalMission mission);
    List<MedicalMission> getAllMedicalMissions();
    MedicalMission getMedicalMissionById(Integer id);

    // Notifications (اختياري)
    NotificationEntity createNotification(NotificationEntity notification);
}