package com.chenyi.yanhuohui.abstractpageread;

import com.chenyi.yanhuohui.primary.manager.Manager;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

/**
 * @Classname DemoPagingItemWriter
 * @Description TODO
 * @Date 2025/1/9 20:00
 * @Created by 陈义
 */
public class DemoPagingItemWriter implements ItemWriter<Manager> {
    @Override
    public void write(List<? extends Manager> items) throws Exception {
        // 将要写入的数据输出到控制台
        System.out.println("write : " + items.toString());
    }
}
