package com.chenyi.yanhuohui.partitionDemo;

import com.chenyi.yanhuohui.common.base.exception.SbcRuntimeException;
import com.chenyi.yanhuohui.manager.Manager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("PartitionSlaveWriter")
@Slf4j
@StepScope
public class PartitionSlaveWriter implements ItemWriter<Manager> {
    @Override
    public void write(List<? extends Manager> list) throws Exception {
        list.stream().forEach(item->{
            if(item.getName().equals("xuyin")){
                throw new SbcRuntimeException("自己抛出的错误");
            }else{
                log.info(Thread.currentThread().getName()+"->"+ item.getName());
            }
        });
    }
}
