package com.libmanage.library_management_system.repository;

import com.libmanage.library_management_system.entity.RoleMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoleMasterRepository extends JpaRepository<RoleMaster, Long>, JpaSpecificationExecutor<RoleMaster> {

}