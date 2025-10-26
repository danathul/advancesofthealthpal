package com.healthpal.service.impl;

import com.healthpal.dto.*;
import com.healthpal.entity.*;
import com.healthpal.repository.*;
import com.healthpal.service.ConsultationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConsultationServiceImpl implements ConsultationService {

    private static final Logger logger = LoggerFactory.getLogger(ConsultationServiceImpl.class);

    private final AppointmentRepository appointmentRepo;
    private final MessageRepository messageRepo;
    private final UserRepository userRepo;
    private final PatientRepository patientRepo;
    private final DoctorRepository doctorRepo;
    private final SupportGroupRepository groupRepo;
    private final GroupMembershipRepository membershipRepo;
    private final GroupMessageRepository groupMsgRepo;
    private final ModelMapper mapper;
    private final RestTemplate restTemplate;

    @Value("https://libretranslate.de/translate")
    private String translateUrl;

    @Override
    public AppointmentDTO bookAppointment(AppointmentDTO dto) {
        Appointment apt = mapper.map(dto, Appointment.class);
        apt.setPatient(patientRepo.findById(dto.getPatientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found")));
        apt.setDoctor(doctorRepo.findById(dto.getDoctorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found")));
        return mapper.map(appointmentRepo.save(apt), AppointmentDTO.class);
    }

    @Override
    public AppointmentDTO getAppointment(Integer id) {
        return mapper.map(appointmentRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found")), AppointmentDTO.class);
    }

    @Override
    public MessageDTO sendMessage(MessageDTO dto) {
        Message msg = mapper.map(dto, Message.class);
        msg.setAppointment(appointmentRepo.findById(dto.getAppointmentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found")));
        msg.setSender(userRepo.findById(dto.getSenderId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")));
        msg.setTranslatedContent(translateMessage(new TranslationDTO(dto.getContent())));
        return mapper.map(messageRepo.save(msg), MessageDTO.class);
    }

    @Override
    public List<MessageDTO> getMessagesForAppointment(Integer id) {
        return messageRepo.findByAppointmentId(id).stream()
                .map(m -> mapper.map(m, MessageDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public String translateMessage(TranslationDTO dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String body = String.format("{\"q\":\"%s\",\"source\":\"%s\",\"target\":\"%s\"}",
                dto.getText(), dto.getSourceLang(), dto.getTargetLang());
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(translateUrl, entity, String.class);
            String json = response.getBody();
            return json.split("\"translatedText\":\"")[1].split("\"")[0];
        } catch (Exception e) {
            logger.error("Translation failed", e);
            return dto.getText() + " [Translation failed]";
        }
    }

    @Override
    public SupportGroupDTO createSupportGroup(SupportGroupDTO dto) {
        SupportGroup group = mapper.map(dto, SupportGroup.class);
        group.setCreatedBy(userRepo.findById(dto.getCreatedBy())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")));
        return mapper.map(groupRepo.save(group), SupportGroupDTO.class);
    }

    @Override
    public void joinSupportGroup(Integer groupId, Integer userId) {
        if (membershipRepo.findBySupportGroupIdAndUserId(groupId, userId).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Already joined");
        }
        GroupMembership m = new GroupMembership();
        m.setSupportGroup(groupRepo.findById(groupId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Group not found")));
        m.setUser(userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")));
        membershipRepo.save(m);
    }

    @Override
    public MessageDTO postGroupMessage(Integer groupId, MessageDTO dto) {
        GroupMessage msg = new GroupMessage();
        msg.setSupportGroup(groupRepo.findById(groupId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Group not found")));
        msg.setSender(userRepo.findById(dto.getSenderId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")));
        msg.setContent(dto.getContent());
        return mapper.map(groupMsgRepo.save(msg), MessageDTO.class);
    }

    @Override
    public List<MessageDTO> getGroupMessages(Integer groupId) {
        return groupMsgRepo.findBySupportGroupId(groupId).stream()
                .map(m -> mapper.map(m, MessageDTO.class))
                .collect(Collectors.toList());
    }
}