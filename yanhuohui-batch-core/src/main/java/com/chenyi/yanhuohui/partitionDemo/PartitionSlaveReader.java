package com.chenyi.yanhuohui.partitionDemo;

import com.chenyi.yanhuohui.manager.Manager;
import com.chenyi.yanhuohui.manager.ManagerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CopyOnWriteArrayList;

@Component("PartitionSlaveReader")
@StepScope
@Slf4j
public class PartitionSlaveReader extends AbstractPagingItemReader<Manager> {

    @Value("#{stepExecutionContext['startTime']}")
    private String startTime;

    @Value("#{stepExecutionContext['endTime']}")
    private String endTime;

    @Autowired
    private ManagerRepository managerRepository;

    @Override
    protected void doReadPage() {
        PageRequest pageRequest = PageRequest.of(getPage(),getPageSize());
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Page<Manager> managers = managerRepository.findByTime(
                LocalDateTime.parse(startTime,df),LocalDateTime.parse(endTime,df),pageRequest);

        if(results==null){
            results = new CopyOnWriteArrayList();
        }else {
            results.clear();
        }
        results.addAll(managers.getContent());

    }

    @Override
    protected void doJumpToPage(int i) {

    }
}
