package com.healthpal.repository;

import com.healthpal.entity.Webinar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebinarRepository extends JpaRepository<Webinar, Integer> {
}
