package com.example.warehousetest.logging.activity.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ActivityLogDto {
    private int id;
    private String username;
    private String activities;
    private LocalDateTime requestTime;

}