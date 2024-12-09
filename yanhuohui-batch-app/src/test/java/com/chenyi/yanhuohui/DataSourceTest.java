package com.chenyi.yanhuohui;

import com.chenyi.yanhuohui.primary.manager.Manager;
import com.chenyi.yanhuohui.primary.manager.ManagerRepository;
import com.chenyi.yanhuohui.wangdai.user.User;
import com.chenyi.yanhuohui.wangdai.user.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.*;

/**
 * @Classname DataSourceTest
 * @Description TODO
 * @Date 2024/12/3 18:45
 * @Created by 陈义
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DataSourceTest {
    @Autowired
    @Qualifier("primaryDataSource")
    private DataSource primaryDataSource;

    @Autowired
    @Qualifier("wangdaiDataSource")
    private DataSource wangdaiDataSource;

// 测试连接

    @Test
    public void contextLoads() throws SQLException {
        Connection connection = primaryDataSource.getConnection();
        DatabaseMetaData metaData = connection.getMetaData();

        //数据源>>>>>>class com.zaxxer.hikari.HikariDataSource
        System.out.println("数据源>>>>>>" + primaryDataSource.getClass());
        System.out.println("连接>>>>>>>>" + connection);
        System.out.println("连接地址>>>>" + connection.getMetaData().getURL());
        System.out.println("驱动名称>>>>" + metaData.getDriverName());
        System.out.println("驱动版本>>>>" + metaData.getDriverVersion());
        System.out.println("数据库名称>>" + metaData.getDatabaseProductName());
        System.out.println("数据库版本>>" + metaData.getDatabaseProductVersion());
        System.out.println("连接用户名称>" + metaData.getUserName());

        connection.close();

        Connection connection2 = wangdaiDataSource.getConnection();
        DatabaseMetaData metaData2 = connection2.getMetaData();

        //数据源>>>>>>class com.zaxxer.hikari.HikariDataSource
        System.out.println("数据源>>>>>>" + wangdaiDataSource.getClass());
        System.out.println("连接>>>>>>>>" + connection2);
        System.out.println("连接地址>>>>" + connection2.getMetaData().getURL());
        System.out.println("驱动名称>>>>" + metaData2.getDriverName());
        System.out.println("驱动版本>>>>" + metaData2.getDriverVersion());
        System.out.println("数据库名称>>" + metaData2.getDatabaseProductName());
        System.out.println("数据库版本>>" + metaData2.getDatabaseProductVersion());
        System.out.println("连接用户名称>" + metaData2.getUserName());

        connection2.close();

        // 数据源>>>>>>class com.zaxxer.hikari.HikariDataSource
        // 连接>>>>>>>>HikariProxyConnection @554510956 wrapping com.mysql.cj.jdbc.ConnectionImpl@3bec5821
        // 连接地址>>>>jdbc:mysql://127.0.0.1:3306/test?characterEncoding=UTF-8&serverTimezone=UTC
        // 驱动名称>>>>MySQL Connector/J
        // 驱动版本>>>>mysql-connector-java-8.0.16 (Revision: 34cbc6bc61f72836e26327537a432d6db7c77de6)
        // 数据库名称>>MySQL
        // 数据库版本>>8.0.26
        // 连接用户名称>root@localhost
    }

    /********手搓数据库连接进行SQL操作***************/
    /********************************************
     * 1.加载JDBC驱动程序
     * 2.创建数据库连接
     * 3.创建Statement对象
     * 4.执行SQL语句
     * 5.处理返回结果
     * 6.关闭创建的对象 ***************************/
    @Test
    public void executeSQLByJDBC(){
        try {
            //加载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //创建练剑
            Connection conn= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/yanhuohui-batch?useSSL=false&autoReconnect=true&characterEncoding=utf8&serverTimezone=GMT%2B8","root","123456");
            //创建Statement对象
            Statement stmt=conn.createStatement();
            //编写SQL
            String querySQL="select * from manager where name = 'chenyi'";
            ResultSet rs=stmt.executeQuery(querySQL);
            while(rs.next()){
                for(int i=1;i<=4;i++){
                    System.out.print(rs.getString(i)+"  ");
                }
                System.out.println();
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    @Autowired
    @Qualifier("wangdaiJdbcTemplate")
    private JdbcTemplate wangdaiJdbcTemplate;

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate primaryJdbcTemplate;

    @Test
    public void testTemplate(){
        String sql1 = "SELECT * FROM user WHERE id = ?";
        User user = wangdaiJdbcTemplate.queryForObject(sql1, new Object[]{1}, new BeanPropertyRowMapper<>(User.class));
        System.out.println("User: " + user);

        String sql2 = "SELECT * FROM manager WHERE id = ?";
        Manager manager = primaryJdbcTemplate.queryForObject(sql2, new Object[]{43}, new BeanPropertyRowMapper<>(Manager.class));
        System.out.println("Manager: " + manager);
    }

    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional("primaryPlatformTransactionManager")  //加上这个注解会导致插入数据自动回滚
    public void testPrimaryPlatformTransactionManager(){
        managerRepository.updateById(43L,"dd");
        //throw new RuntimeException();
    }

    @Test
    //@Transactional("wangdaiPlatformTransactionManager")
    public void testWangdaiPlatformTransactionManager(){
        User user = User.builder().name("zhumengze").age("20").build();
        userRepository.save(user);
        //throw new RuntimeException();
    }

}
