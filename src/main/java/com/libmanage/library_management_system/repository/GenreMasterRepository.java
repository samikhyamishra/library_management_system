package com.libmanage.library_management_system.repository;

import com.libmanage.library_management_system.entity.GenreMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GenreMasterRepository extends JpaRepository<GenreMaster, Long>, JpaSpecificationExecutor<GenreMaster> {

}