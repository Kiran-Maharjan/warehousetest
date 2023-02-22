package com.example.warehousetest.logging.activity.repository;

import com.example.warehousetest.logging.activity.model.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ActivityRepository extends JpaRepository<ActivityLog, String> {
}