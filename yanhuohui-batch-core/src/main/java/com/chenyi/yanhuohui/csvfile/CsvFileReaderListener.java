package com.chenyi.yanhuohui.csvfile;

import com.chenyi.yanhuohui.manager.Manager;
import org.springframework.batch.core.ItemReadListener;

/**
 * @Classname CsvFileReaderListener
 * @Description TODO
 * @Date 2024/11/13 14:55
 * @Created by 陈义
 */
public class CsvFileReaderListener implements ItemReadListener<Manager> {

    @Override
    public void beforeRead() {
        System.out.println("Starting to read a record.");
    }

    @Override
    public void afterRead(Manager item) {
        System.out.println("Successfully read: " + item);
    }

    @Override
    public void onReadError(Exception ex) {
        System.out.println("Error reading record: " + ex.getMessage());
    }
}
