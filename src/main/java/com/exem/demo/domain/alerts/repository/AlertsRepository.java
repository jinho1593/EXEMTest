package com.exem.demo.domain.alerts.repository;

import com.exem.demo.domain.alerts.entity.Alerts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertsRepository extends JpaRepository<Alerts, Long> {
}
