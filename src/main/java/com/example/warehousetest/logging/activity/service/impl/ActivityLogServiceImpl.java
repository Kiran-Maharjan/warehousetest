package com.example.warehousetest.logging.activity.service.impl;

import com.example.warehousetest.logging.activity.model.ActivityLog;
import com.example.warehousetest.logging.activity.repository.ActivityRepository;
import com.example.warehousetest.logging.activity.service.IActivityLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityLogServiceImpl implements IActivityLogService {

    @Autowired
    private ActivityRepository activityRepository;


    public void saveApplicationLog(ActivityLog activityLog) {
        activityRepository.save(activityLog);
    }

}