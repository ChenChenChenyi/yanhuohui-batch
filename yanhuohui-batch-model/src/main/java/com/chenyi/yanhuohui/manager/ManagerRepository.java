package com.chenyi.yanhuohui.manager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ManagerRepository extends JpaRepository<Manager,Long> {

    List<Manager> findByName(String name);

    @Query(
            value = "select * from manager as u where u.name = :name",
            nativeQuery = true)
    Manager findByNameNativeQuery(@Param("name") String name);

    @Query(value = "select count(1) from manager where create_time between :startTime and :endTime",
            nativeQuery = true)
    int countByLocalDate(@Param("startTime") LocalDate startTime,@Param("endTime") LocalDate endTime);

    @Query(value = "select create_time from manager where create_time between :startTime and :endTime",
            nativeQuery = true)
    Page<String> findMinTimeByPage(@Param("startTime") LocalDate startTime,@Param("endTime") LocalDate endTime, Pageable pageable);

    @Query(value = "select * from manager where create_time between :startTime and :endTime",
            nativeQuery = true)
    Page<Manager> findByTime(@Param("startTime") LocalDateTime startTime,@Param("endTime") LocalDateTime endTime, Pageable pageable);
}
