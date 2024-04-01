package com.exem.demo.domain.stationMaintenance.repository;

import com.exem.demo.domain.stationMaintenance.entity.StationMaintenance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationMaintenanceRepository extends JpaRepository<StationMaintenance, Long> {
}
