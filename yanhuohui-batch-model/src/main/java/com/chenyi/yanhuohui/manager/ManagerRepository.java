package com.chenyi.yanhuohui.manager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ManagerRepository extends JpaRepository<Manager,Long> {

    @Query(
            value = "select * from manager as u where u.name = :name",
            nativeQuery = true)
    Manager findByNameNativeQuery(@Param("name") String name);
}
