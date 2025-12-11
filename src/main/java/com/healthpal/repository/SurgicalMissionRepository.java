package com.healthpal.repository;

import com.healthpal.entity.SurgicalMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurgicalMissionRepository extends JpaRepository<SurgicalMission, Integer> {
}
