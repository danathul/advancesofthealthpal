package com.healthpal.repository;

import com.healthpal.entity.MissionVolunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MissionVolunteerRepository extends JpaRepository<MissionVolunteer, Integer> {
}
