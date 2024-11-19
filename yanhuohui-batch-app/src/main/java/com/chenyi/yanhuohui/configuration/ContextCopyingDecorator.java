package com.chenyi.yanhuohui.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskDecorator;

/**
 * @Classname ContextCopyingDecorator
 * @Description TODO
 * @Date 2024/11/18 10:27
 * @Created by 陈义
 */
@Slf4j
public class ContextCopyingDecorator implements TaskDecorator {
    @Override
    public Runnable decorate(Runnable runnable) {
        //获取主线程中设置的 user对象
        Object user = ThreadLocalUtil.get(ThreadLocalUtil.USER_KEY).orElse(null);
        return () -> {
            try {
                //把user对象重新copy传递给子线程
                if (user != null) {
                    ThreadLocalUtil.set(ThreadLocalUtil.USER_KEY, user);
                }
                runnable.run();
            } finally {
                ThreadLocalUtil.remove();
            }
        };
    }
}
