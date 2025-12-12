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

import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
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

    @Value("https://api.mymemory.translated.net/get")
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
    // تأكد انك عامل import لهذه المكتبة فوق في الملف
    // import org.springframework.web.util.UriComponentsBuilder;

    // ⚠️ تأكد من إضافة هذا الـ import فوق مع باقي الـ imports

    @Override
    public String translateMessage(TranslationDTO dto) {
        // 1. ضمان وجود قيم افتراضية حتى لو الـ DTO وصل ناقص
        String src = (dto.getSourceLang() != null && !dto.getSourceLang().isEmpty()) ? dto.getSourceLang() : "ar";
        String tgt = (dto.getTargetLang() != null && !dto.getTargetLang().isEmpty()) ? dto.getTargetLang() : "en";
        String textToTranslate = dto.getText();

        try {
            logger.info("Translating: '{}' from {} to {}", textToTranslate, src, tgt);

            // 2. بناء الرابط كـ URI Object
            // استخدام .toUri() يمنع RestTemplate من إعادة تشفير الرموز وتخريب الـ |
            URI uri = org.springframework.web.util.UriComponentsBuilder
                    .fromHttpUrl("https://api.mymemory.translated.net/get")
                    .queryParam("q", textToTranslate)
                    .queryParam("langpair", src + "|" + tgt)
                    .build()
                    .toUri(); // 👈 هذا هو سر الحل

            // 3. الاتصال بالسيرفر
            ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

            // 4. استخراج النتيجة
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                String json = response.getBody();
                // تحليل يدوي سريع للـ JSON
                if (json.contains("\"translatedText\":\"")) {
                    String[] parts = json.split("\"translatedText\":\"");
                    if (parts.length > 1) {
                        return parts[1].split("\"")[0];
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Translation API Error: {}", e.getMessage());
        }

        // Fallback في حال الفشل التام
        return textToTranslate + " (Simulated Translation)";
    }
    @Override
    public SupportGroupDTO createSupportGroup(SupportGroupDTO dto) {
        // 1. تجهيز الـ Entity
        SupportGroup group = new SupportGroup();
        group.setName(dto.getName());
        group.setDescription(dto.getDescription());

        // 2. ربط المستخدم (User)
        User user = userRepo.findById(dto.getCreatedBy())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        group.setCreatedBy(user);

        // 3. الحفظ في الداتا بيس
        SupportGroup savedGroup = groupRepo.save(group);

        // 4. التحويل اليدوي للرد (عشان نتجنب خطأ ModelMapper)
        SupportGroupDTO responseDto = new SupportGroupDTO();
        responseDto.setId(savedGroup.getId());
        responseDto.setName(savedGroup.getName());
        responseDto.setDescription(savedGroup.getDescription());
        responseDto.setCreatedBy(savedGroup.getCreatedBy().getId()); // 👈 هنا السر: بناخد الـ ID بس

        return responseDto;
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