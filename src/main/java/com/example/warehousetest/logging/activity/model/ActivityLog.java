package com.example.warehousetest.logging.activity.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ActivityLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String username;
    @Column(columnDefinition="TEXT")
    private String operation;
    @Column(columnDefinition="TEXT")
    private String endPoint;
    @Column(columnDefinition="TEXT")
    private String method;
    @Column(columnDefinition="TEXT")
    private String params;
    @Column(columnDefinition="TEXT")
    private String response;
    @Column(columnDefinition="TEXT")
    private String activities;
    private Date requestTime;

}