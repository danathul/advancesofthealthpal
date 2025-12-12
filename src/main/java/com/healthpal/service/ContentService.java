package com.healthpal.service;

import com.healthpal.dto.*;
import com.healthpal.entity.*;
import java.util.List;
//rahaf's part
public interface ContentService {


    HealthGuide createHealthGuide(HealthGuide guide);
    List<HealthGuide> getAllHealthGuides();
    HealthGuide getHealthGuideById(Integer id);
    HealthGuide updateHealthGuide(Integer id, HealthGuide guide);
    void deleteHealthGuide(Integer id);


    Webinar createWebinar(WebinarCreateDTO dto);
    List<Webinar> getAllWebinars();
    Webinar getWebinarById(Integer id);


    Workshop createWorkshop(WorkshopCreateDTO dto);
    List<Workshop> getAllWorkshops();
    Workshop getWorkshopById(Integer id);


    MedicalMission createMedicalMission(MedicalMission mission);
    List<MedicalMission> getAllMedicalMissions();
    MedicalMission getMedicalMissionById(Integer id);


    NotificationEntity createNotification(NotificationEntity notification);
}