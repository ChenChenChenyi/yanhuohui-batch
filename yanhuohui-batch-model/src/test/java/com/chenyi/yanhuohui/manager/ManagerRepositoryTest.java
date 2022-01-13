package com.chenyi.yanhuohui.manager;

import com.chenyi.yanhuohui.YanhuohuiBatchTestApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = YanhuohuiBatchTestApplication.class)
@Slf4j
public class ManagerRepositoryTest {

    @Autowired
    private ManagerRepository managerRepository;

    @Test
    public void testFindByName(){
        List<Manager> managerList = managerRepository.findByName("maomao");
        for(Manager manager : managerList){
            log.info(manager.toString());
        }
    }

    @Test
    public void testFindAll(){
        List<Manager> managerList = managerRepository.findAll();
        for(Manager manager : managerList){
            log.info(manager.toString());
        }
    }

    @Test
    public void testDTO(){
        List<ManagerDTO> managerList = managerRepository.findGroupByName("maomao%");
        for(ManagerDTO manager : managerList){
            log.info(manager.toString());
        }
    }

    @Test
    public void testLikeName(){
        List<Manager> managerList = managerRepository.findByNameLike("maomao%");
        for(Manager manager : managerList){
            log.info(manager.toString());
        }
    }


}
