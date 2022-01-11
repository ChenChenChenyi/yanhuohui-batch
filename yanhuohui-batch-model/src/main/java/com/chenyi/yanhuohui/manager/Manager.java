package com.chenyi.yanhuohui.manager;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "role")
    @NotNull
    private String role;

    @Column(name = "create_time")
    private LocalDateTime createTime;
}
