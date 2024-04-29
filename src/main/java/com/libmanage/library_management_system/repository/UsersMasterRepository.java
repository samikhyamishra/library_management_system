package com.libmanage.library_management_system.repository;

import com.libmanage.library_management_system.entity.UsersMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UsersMasterRepository extends JpaRepository<UsersMaster, Integer> {

    Optional<UsersMaster> findByUserName(String userName);
}