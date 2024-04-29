package com.libmanage.library_management_system.repository;

import com.libmanage.library_management_system.entity.MenuMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MenuMasterRepository extends JpaRepository<MenuMaster, Long>, JpaSpecificationExecutor<MenuMaster> {

}