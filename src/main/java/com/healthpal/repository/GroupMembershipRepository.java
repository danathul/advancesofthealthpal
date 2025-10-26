package com.healthpal.repository;

import com.healthpal.entity.GroupMembership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupMembershipRepository extends JpaRepository<GroupMembership, Integer> {
    Optional<GroupMembership> findBySupportGroupIdAndUserId(Integer groupId, Integer userId);
}