package com.libmanage.library_management_system.repository;

import com.libmanage.library_management_system.entity.BookMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookMasterRepository extends JpaRepository<BookMaster, Long>, JpaSpecificationExecutor<BookMaster> {

}