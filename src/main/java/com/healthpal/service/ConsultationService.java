package com.healthpal.service;

import com.healthpal.dto.*;
import java.util.List;

public interface ConsultationService {
    AppointmentDTO bookAppointment(AppointmentDTO dto);
    AppointmentDTO getAppointment(Integer id);
    MessageDTO sendMessage(MessageDTO dto);
    List<MessageDTO> getMessagesForAppointment(Integer appointmentId);
    String translateMessage(TranslationDTO dto);
    SupportGroupDTO createSupportGroup(SupportGroupDTO dto);
    void joinSupportGroup(Integer groupId, Integer userId);
    MessageDTO postGroupMessage(Integer groupId, MessageDTO dto);
    List<MessageDTO> getGroupMessages(Integer groupId);
}