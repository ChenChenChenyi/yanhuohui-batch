package com.chenyi.yanhuohui;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * @Classname DataSourceTest
 * @Description TODO
 * @Date 2024/12/3 18:45
 * @Created by 陈义
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DataSourceTest {
    /**
     * Spring Boot 默认已经配置好了数据源，程序员可以直接 DI 注入然后使用即可
     */
    @Resource
    DataSource dataSource;
    @Test
    public void contextLoads() throws SQLException {
        Connection connection = dataSource.getConnection();
        DatabaseMetaData metaData = connection.getMetaData();

        //数据源>>>>>>class com.zaxxer.hikari.HikariDataSource
        System.out.println("数据源>>>>>>" + dataSource.getClass());
        System.out.println("连接>>>>>>>>" + connection);
        System.out.println("连接地址>>>>" + connection.getMetaData().getURL());
        System.out.println("驱动名称>>>>" + metaData.getDriverName());
        System.out.println("驱动版本>>>>" + metaData.getDriverVersion());
        System.out.println("数据库名称>>" + metaData.getDatabaseProductName());
        System.out.println("数据库版本>>" + metaData.getDatabaseProductVersion());
        System.out.println("连接用户名称>" + metaData.getUserName());

        connection.close();

        // 数据源>>>>>>class com.zaxxer.hikari.HikariDataSource
        // 连接>>>>>>>>HikariProxyConnection @554510956 wrapping com.mysql.cj.jdbc.ConnectionImpl@3bec5821
        // 连接地址>>>>jdbc:mysql://127.0.0.1:3306/test?characterEncoding=UTF-8&serverTimezone=UTC
        // 驱动名称>>>>MySQL Connector/J
        // 驱动版本>>>>mysql-connector-java-8.0.16 (Revision: 34cbc6bc61f72836e26327537a432d6db7c77de6)
        // 数据库名称>>MySQL
        // 数据库版本>>8.0.26
        // 连接用户名称>root@localhost
    }
}
