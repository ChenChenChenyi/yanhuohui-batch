package com.chenyi.yanhuohui.csvfile;

import com.chenyi.yanhuohui.manager.Manager;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;

import java.time.LocalDateTime;

/**
 * @Classname CsvItemProcessor
 * @Description TODO
 * @Date 2024/11/12 18:29
 * @Created by 陈义
 */
public class CsvItemProcessor extends ValidatingItemProcessor<Manager> {
    @Override
    public Manager process(Manager item) throws ValidationException {
        /*
         * 需要执行super.process(item)才会调用自定义校验器
         */
        super.process(item);
        /*
         * 对数据进行简单的处理和转换 todo
         */
        item.setCreateTime(LocalDateTime.now());
        return item;
    }
}
