package com.libmanage.library_management_system.repository;

import com.libmanage.library_management_system.entity.AuthorMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AuthorMasterRepository extends JpaRepository<AuthorMaster, Long>, JpaSpecificationExecutor<AuthorMaster> {

}