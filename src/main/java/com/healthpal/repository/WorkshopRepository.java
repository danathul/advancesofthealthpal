package com.healthpal.repository;

import com.healthpal.entity.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//rahaf's part
@Repository
public interface WorkshopRepository extends JpaRepository<Workshop, Integer> {
}
