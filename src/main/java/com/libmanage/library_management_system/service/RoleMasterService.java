package com.libmanage.library_management_system.service;

import com.libmanage.library_management_system.entity.RoleMaster;
import com.libmanage.library_management_system.repository.RoleMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleMasterService {
    @Autowired
    private RoleMasterRepository roleMasterRepository;

//    fetch all the role
    public List<RoleMaster> findAll(RoleMaster role){
        return roleMasterRepository.findAll();
    }

//    fetch role by specific id
    public  RoleMaster findById(Long roleId){
        RoleMaster role = roleMasterRepository.findById(roleId).orElseThrow(()-> new RuntimeException("Invalid Role Id"));
        return role;
    }

//    create role
    public void createRole(RoleMaster role){
        this.roleMasterRepository.save(role);
    }
//    delete role
    public void deleteRole(Long roleId){
        RoleMaster role = roleMasterRepository.findById(roleId).orElseThrow(()-> new RuntimeException("Invalid Role Id"));
roleMasterRepository.deleteById(roleId);
    }
}
