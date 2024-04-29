package com.libmanage.library_management_system.repository;

import com.libmanage.library_management_system.entity.RoleMenuPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoleMenuPermissionRepository extends JpaRepository<RoleMenuPermission, Long>, JpaSpecificationExecutor<RoleMenuPermission> {

}