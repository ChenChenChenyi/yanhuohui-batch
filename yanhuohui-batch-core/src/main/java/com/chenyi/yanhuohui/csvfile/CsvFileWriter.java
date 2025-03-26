package com.chenyi.yanhuohui.csvfile;

import com.chenyi.yanhuohui.primary.manager.Manager;
import com.chenyi.yanhuohui.primary.manager.ManagerRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname CsvFileWriter
 * @Description TODO
 * @Date 2025/1/23 9:06
 * @Created by 陈义
 */
public class CsvFileWriter implements ItemWriter<Manager> {

    @Autowired
    private ManagerRepository managerRepository;

    @Override
    public void write(List<? extends Manager> list) throws Exception {
        List<Manager> managerList = new ArrayList<>();
        for(Manager item:list){
            //writer里面出现的错误只会使当前chunk内的数据全部回滚，并中断当前线程，不进行后续的数据处理
            if("wujiangtao".equals(item.getName())){
                throw new RuntimeException("手动抛出的错误！");
            }
            item.setCreateTime(LocalDateTime.now());
            managerList.add(item);
        }
        managerRepository.saveAll(managerList);
    }
}
