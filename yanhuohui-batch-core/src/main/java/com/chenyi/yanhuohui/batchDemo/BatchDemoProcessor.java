package com.chenyi.yanhuohui.batchDemo;

import com.chenyi.yanhuohui.client.ClientRepository;
import com.chenyi.yanhuohui.manager.Manager;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.validation.ValidationException;

public class BatchDemoProcessor extends ValidatingItemProcessor<Manager> {

    @Override
    public Manager process(Manager item) throws ValidationException {
        /**
         * 需要执行super.process(item)才会调用自定义校验器
         */
        super.process(item);
        if (item.getRole().equals("aa")) {
            item.setRole("A");
        } else {
            item.setRole("B");
        }
        return item;
    }
}
