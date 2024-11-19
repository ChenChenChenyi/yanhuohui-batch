package com.chenyi.yanhuohui.csvfile;

import com.chenyi.yanhuohui.manager.Manager;
import org.springframework.batch.core.ItemWriteListener;

import java.util.List;

/**
 * @Classname CsvFileWriterListener
 * @Description TODO
 * @Date 2024/11/13 15:42
 * @Created by 陈义
 */
public class CsvFileWriterListener implements ItemWriteListener<Manager> {
    @Override
    public void beforeWrite(List<? extends Manager> list) {
        System.out.println("Starting to write record:" + list.toString());
    }

    @Override
    public void afterWrite(List<? extends Manager> list) {
        System.out.println("Successfully write: " + list.toString());
    }

    @Override
    public void onWriteError(Exception e, List<? extends Manager> list) {
        System.out.println("Error writing record: " + e.getMessage());
    }
}
