package com.healthpal.service.impl;

import com.healthpal.dto.*;
import com.healthpal.entity.*;
import com.healthpal.repository.*;
import com.healthpal.service.SponsorshipAndResourcesService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SponsorshipAndResourcesServiceImpl implements SponsorshipAndResourcesService {

    private final TreatmentCaseRepository caseRepo;
    private final DonationRepository donationRepo;
    private final MedicineRequestRepository medicineReqRepo;
    private final EquipmentRepository equipmentRepo;
    private final PatientRepository patientRepo;
    private final UserRepository userRepo;
    private final ModelMapper mapper;

    @Override
    public TreatmentCaseDTO createTreatmentCase(TreatmentCaseDTO dto) {
        TreatmentCase entity = mapper.map(dto, TreatmentCase.class);
        entity.setPatient(patientRepo.findById(dto.getPatientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found")));
        entity.setCollectedAmount(BigDecimal.ZERO);
        entity.setStatus("OPEN");
        entity.setCreatedAt(LocalDateTime.now());

        TreatmentCase saved = caseRepo.save(entity);
        TreatmentCaseDTO result = mapper.map(saved, TreatmentCaseDTO.class);
        result.setProgressPercentage(calculateProgress(saved));
        return result;
    }

    @Override
    public List<TreatmentCaseDTO> getOpenCases() {
        return caseRepo.findByStatus("OPEN").stream()
                .map(c -> {
                    TreatmentCaseDTO dto = mapper.map(c, TreatmentCaseDTO.class);
                    dto.setProgressPercentage(calculateProgress(c));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public TreatmentCaseDTO getCaseWithProgress(Integer id) {
        TreatmentCase c = caseRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Case not found"));
        TreatmentCaseDTO dto = mapper.map(c, TreatmentCaseDTO.class);
        dto.setProgressPercentage(calculateProgress(c));
        return dto;
    }

    @Override
    public DonationDTO makeDonation(DonationDTO dto) {
        TreatmentCase treatmentCase = caseRepo.findById(dto.getCaseId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Case not found"));

        Donation donation = mapper.map(dto, Donation.class);
        donation.setTreatmentCase(treatmentCase);
        if (dto.getDonorId() != null) {
            donation.setDonor(userRepo.findById(dto.getDonorId()).orElse(null));
        }
        donation.setDonatedAt(LocalDateTime.now());

        Donation saved = donationRepo.save(donation);

        BigDecimal newCollected = treatmentCase.getCollectedAmount().add(dto.getAmount());
        treatmentCase.setCollectedAmount(newCollected);
        if (newCollected.compareTo(treatmentCase.getRequiredAmount()) >= 0) {
            treatmentCase.setStatus("FULLY_FUNDED");
        }
        caseRepo.save(treatmentCase);

        return mapper.map(saved, DonationDTO.class);
    }

    // الدالة الناقصة الأولى
    @Override
    public List<DonationDTO> getDonationsForCase(Integer caseId) {
        caseRepo.findById(caseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Case not found"));
        return donationRepo.findByTreatmentCaseId(caseId).stream()
                .map(d -> mapper.map(d, DonationDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public MedicineRequestDTO createMedicineRequest(MedicineRequestDTO dto) {
        MedicineRequest entity = mapper.map(dto, MedicineRequest.class);
        entity.setPatient(patientRepo.findById(dto.getPatientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found")));
        entity.setStatus("PENDING");
        entity.setCreatedAt(LocalDateTime.now());

        MedicineRequest saved = medicineReqRepo.save(entity);
        return mapper.map(saved, MedicineRequestDTO.class);
    }

    // الدالة الناقصة الثانية
    @Override
    public List<MedicineRequestDTO> getPendingMedicineRequests() {
        return medicineReqRepo.findByStatus("PENDING").stream()
                .map(m -> mapper.map(m, MedicineRequestDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public MedicineRequestDTO fulfillMedicineRequest(Integer requestId, Integer userId) {
        MedicineRequest req = medicineReqRepo.findById(requestId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found"));

        req.setFulfilledBy(userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")));        req.setStatus("FULFILLED");

        return mapper.map(medicineReqRepo.save(req), MedicineRequestDTO.class);
    }

    // الدالة الناقصة الثالثة
    @Override
    public List<EquipmentDTO> getAvailableEquipment() {
        return equipmentRepo.findByQuantityAvailableGreaterThan(0).stream()
                .map(e -> mapper.map(e, EquipmentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public EquipmentDTO addEquipment(EquipmentDTO dto) {
        Equipment entity = mapper.map(dto, Equipment.class);
        entity.setListedAt(LocalDateTime.now());
        Equipment saved = equipmentRepo.save(entity);
        return mapper.map(saved, EquipmentDTO.class);
    }

    // لو عايز تحتفظ بيها (مش ضرورية حاليًا لكن خلينا ننظف التحذير)
    public EquipmentDTO lendEquipment(Integer equipmentId, Integer borrowerId) {
        Equipment eq = equipmentRepo.findById(equipmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipment not found"));
        if (eq.getQuantityAvailable() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No equipment available");
        }
        eq.setQuantityAvailable(eq.getQuantityAvailable() - 1);
        equipmentRepo.save(eq);
        return mapper.map(eq, EquipmentDTO.class);
    }

    private double calculateProgress(TreatmentCase c) {
        if (c.getRequiredAmount() == null || c.getRequiredAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return 100.0;
        }
        return c.getCollectedAmount()
                .divide(c.getRequiredAmount(), 4, RoundingMode.HALF_EVEN)
                .multiply(BigDecimal.valueOf(100))
                .doubleValue();
    }
}