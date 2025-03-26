package com.chenyi.yanhuohui.managerservice;

import com.chenyi.yanhuohui.primary.manager.Manager;
import com.chenyi.yanhuohui.primary.manager.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ManagerService {

    @Autowired
    private ManagerRepository managerRepository;

    @Transactional
    void updateById(Manager manager){
        
    }
}
