package com.chenyi.yanhuohui.csvfile;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;

/**
 * @Classname ItemCountItemStream
 * @Description TODO
 * @Date 2024/11/18 12:22
 * @Created by 陈义
 */
public class ItemCountItemStream implements ItemStream {

    public void open(ExecutionContext executionContext) throws ItemStreamException {
    }

    public void update(ExecutionContext executionContext) throws ItemStreamException {
        //log the record count periodically
        System.out.println("当前读取到的数据条数为ItemCount: "+executionContext.get("FlatFileItemReader.read.count"));
    }

    public void close() throws ItemStreamException {
    }
}
