package com.chenyi.yanhuohui.manager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

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

    @Query(value = "select new com.chenyi.yanhuohui.manager.ManagerDTO(m.id,m.name) from Manager m where m.name like ?1")
    List<ManagerDTO> findGroupByName(String name);

    /**
     * 这个有个坑，用对象查询的语句时Manager这个实体对象对应的写法是Manager实体上Entity注解里面的name的值，默认是类的名字
     * 所以写成select m from manager m where m.name like ?1时总是报错，这里不是manager而是Manager
     */
    @Query(value = "select m from Manager m where m.name like ?1")
    List<Manager> findByNameLike(String name);

    @Modifying
    @Transactional
    @Query("update Manager set role = ?2 where id = ?1")
    int updateById(Long id,String role);

}
