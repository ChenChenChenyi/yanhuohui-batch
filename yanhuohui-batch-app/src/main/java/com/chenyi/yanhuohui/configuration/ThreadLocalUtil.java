package com.chenyi.yanhuohui.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @Classname ThreadLocalUtil
 * @Description TODO
 * @Date 2024/11/18 10:27
 * @Created by 陈义
 */
public class ThreadLocalUtil {
    public static final String USER_KEY = "user";

    private static final ThreadLocal<Map<String, Object>> THREAD_LOCAL = ThreadLocal.withInitial(() -> new HashMap<>(4));
    public static Optional<Object> get(String key) {
        return Optional.ofNullable(THREAD_LOCAL.get().get(key));
    }
    public static void set(String key, Object obj) {
        THREAD_LOCAL.get().put(key, obj);
    }
    public static void remove() {
        THREAD_LOCAL.remove();
    }
}
