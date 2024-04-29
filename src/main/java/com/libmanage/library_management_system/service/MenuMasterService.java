package com.libmanage.library_management_system.service;

import com.libmanage.library_management_system.entity.MenuMaster;
import com.libmanage.library_management_system.repository.MenuMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuMasterService {

    @Autowired
    private MenuMasterRepository menuMasterRepository;

//    fetch all the menu
    public List<MenuMaster> findAll(){
        return menuMasterRepository.findAll();
    }

//    fetch menu by specific id
    public MenuMaster findById(Long menuId){
        MenuMaster menu = menuMasterRepository.findById(menuId).orElseThrow(()-> new RuntimeException("Invalid Menu Id."));
        return menu;
    }

//    create new menu
    public void createMenu(MenuMaster menu){
        this.menuMasterRepository.save(menu);
    }
//    remove menu
    public void deleteMenu(Long menuId){
        MenuMaster menu = menuMasterRepository.findById(menuId).orElseThrow(()-> new RuntimeException("Invalid Menu Id"));
        menuMasterRepository.deleteById(menuId);
    }
}
