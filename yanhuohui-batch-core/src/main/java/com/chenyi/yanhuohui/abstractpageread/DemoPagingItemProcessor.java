package com.chenyi.yanhuohui.abstractpageread;

import com.chenyi.yanhuohui.primary.manager.Manager;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @Classname DemoPagingItemProcessor
 * @Description TODO
 * @Date 2025/1/9 19:55
 * @Created by 陈义
 */
public class DemoPagingItemProcessor implements ItemProcessor<Manager,Manager> {

    @Override
    public Manager process(Manager manager) throws Exception {
        manager.setCreateTime(LocalDateTime.now());
        return manager;
    }
}
