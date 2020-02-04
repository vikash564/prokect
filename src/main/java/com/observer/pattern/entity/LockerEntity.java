package com.observer.pattern.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Setter
@Getter
@Table(name = "TBL_LOCKER")
public class LockerEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", length = 10, nullable = false)
    private Long id;

    @Column(name = "CREATED_AT", nullable = false)
    private Long createdAt;

    @Column(name = "LOCKER_ID", nullable = false)
    private Long lockerId;

    @Column(name = "DOOR_ID", nullable = false)
    private Long doorId;

    @Column(name = "STATUS", nullable = false)
    private String status;


}
