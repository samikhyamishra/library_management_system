package com.libmanage.library_management_system.service;

import com.libmanage.library_management_system.entity.RoleMenuPermission;
import com.libmanage.library_management_system.repository.RoleMenuPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleMenuPermissionService {
    @Autowired
    private RoleMenuPermissionRepository roleMenuPermissionRepository;

//    fetch all permission
    public List<RoleMenuPermission> findAllPermission(){
        return roleMenuPermissionRepository.findAll();
    }

//    fetch permission by role id
//    public RoleMenuPermission findAllById(Long roleId){
//        RoleMenuPermission permission = roleMenuPermissionRepository.findAllById(roleId).orElseThrow(()->new RuntimeException("No such permission permitted for this role id"));
//
//    }

}
