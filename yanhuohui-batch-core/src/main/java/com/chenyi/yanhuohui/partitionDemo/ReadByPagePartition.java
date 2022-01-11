package com.chenyi.yanhuohui.partitionDemo;

import com.chenyi.yanhuohui.manager.ManagerRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class ReadByPagePartition implements Partitioner {
    public static final String START_TIME = "startTime";
    public static final String END_TIME = "endTime";

    @Autowired
    private ManagerRepository managerRepository;

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) { //gridSize为每个slave处理的数据个数
        Map<String, ExecutionContext> resultMap = new HashMap<>();

        int totalNum = managerRepository.countByLocalDate(LocalDate.of(2021,12,31),
                LocalDate.of(2022, 1, 1));

        int pageSize = gridSize;
        int pageNum = totalNum/pageSize;
        if(totalNum%pageSize>0){
            pageNum++;
        }
        for(int i=0;i<pageSize;i++){
            Pageable pageable = PageRequest.of(i,pageNum,Sort.by(new Sort.Order(Sort.Direction.ASC,"create_time")));
            Page<String> minTime = managerRepository.findMinTimeByPage(LocalDate.of(2021,12,31),
                    LocalDate.of(2022, 1, 1),pageable);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            List<String> dateTimes = minTime.toList();
            String startTime = df.format(dateTimes.get(0));
            String endTime = df.format(dateTimes.get(dateTimes.size()-1));
            ExecutionContext executionContext = new ExecutionContext();
            executionContext.putString(START_TIME,startTime);
            executionContext.putString(END_TIME,endTime);
            resultMap.put("partition"+i,executionContext);
        }
        log.info("分片信息partition如下：{}",resultMap);
        return resultMap;
    }
}
