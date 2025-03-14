package com.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.user.entity.UserGroup;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
	Optional<UserGroup> findByGroupName(String groupName);
	@Query("SELECT COUNT(u) FROM UserDetails u JOIN u.groups g WHERE g.id = :groupId")
    Long countUsersByGroupId(@Param("groupId") Long groupId);
}
