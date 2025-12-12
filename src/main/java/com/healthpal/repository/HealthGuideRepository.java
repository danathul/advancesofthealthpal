package com.healthpal.repository;

import com.healthpal.entity.HealthGuide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//rahaf's part
@Repository
public interface HealthGuideRepository extends JpaRepository<HealthGuide, Integer> {
}
