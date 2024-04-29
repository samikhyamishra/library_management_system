package com.libmanage.library_management_system.repository;

import com.libmanage.library_management_system.entity.BorrowedBookMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BorrowedBookMasterRepository extends JpaRepository<BorrowedBookMaster, Integer>{

}