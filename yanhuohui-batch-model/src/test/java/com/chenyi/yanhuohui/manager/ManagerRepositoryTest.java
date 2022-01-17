package com.chenyi.yanhuohui.manager;

import com.chenyi.yanhuohui.YanhuohuiBatchTestApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = YanhuohuiBatchTestApplication.class)
@Slf4j
public class ManagerRepositoryTest {

    @Autowired
    private ManagerRepository managerRepository;

    @Mock
    List<String> mockedListRe;

    @Test
    public void testList(){
        // 创建模拟对象
        List<String> mockList = mock(List.class);

        when(mockList.get(0)).thenReturn("first");
        when(mockList.get(1)).thenThrow(new IndexOutOfBoundsException());

        when(mockedListRe.get(0)).thenReturn("result0");

        log.info(mockList.get(0));
        log.info(mockedListRe.get(0));

        //会报错
        //log.info(mockList.get(1));

        //返回null
        log.info(mockList.get(999));

        //验证mockList.get(0)方法被调用过一次
        verify(mockList).get(0);

        //assertThat(mockList.get(0),is);
    }


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
            //Assert.
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

    @Test
    @Rollback(value = true)
    public void testUpdateById(){
        int result = managerRepository.updateById(269L,String.valueOf(40));
        log.info("更新ID为{}的用户role变为{}",269,40);
    }




}
