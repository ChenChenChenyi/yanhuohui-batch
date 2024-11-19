package com.chenyi.yanhuohui.pageitem;

import com.chenyi.yanhuohui.manager.Manager;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * @Classname ManagerRowMapper
 * @Description TODO
 * @Date 2024/11/19 18:34
 * @Created by 陈义
 */
public class ManagerRowMapper implements RowMapper<Manager> {
    @Override
    public Manager mapRow(ResultSet rs, int rowNum) throws SQLException {

        Manager emp = new Manager();

        emp.setId(rs.getLong("id"));
        emp.setName(rs.getString("name"));
        emp.setRole(rs.getString("role"));
        emp.setCreateTime(rs.getObject("create_time", LocalDateTime.class));

        return emp;
    }
}
