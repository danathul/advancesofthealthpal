// SponsorshipAndResourcesService.java (interface)
package com.healthpal.service;

import com.healthpal.dto.*;
import java.util.List;

public interface SponsorshipAndResourcesService {
    TreatmentCaseDTO createTreatmentCase(TreatmentCaseDTO dto);
    List<TreatmentCaseDTO> getOpenCases();
    TreatmentCaseDTO getCaseWithProgress(Integer id);
    DonationDTO makeDonation(DonationDTO dto);
    List<DonationDTO> getDonationsForCase(Integer caseId);

    MedicineRequestDTO createMedicineRequest(MedicineRequestDTO dto);
    List<MedicineRequestDTO> getPendingMedicineRequests();
    MedicineRequestDTO fulfillMedicineRequest(Integer requestId, Integer userId);

    List<EquipmentDTO> getAvailableEquipment();
    EquipmentDTO addEquipment(EquipmentDTO dto);

}