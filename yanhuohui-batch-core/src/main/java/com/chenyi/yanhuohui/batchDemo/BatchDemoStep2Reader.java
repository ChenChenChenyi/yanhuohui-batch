package com.chenyi.yanhuohui.batchDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class BatchDemoStep2Reader {
    @Autowired
    private DataSource dataSource;

    
}
