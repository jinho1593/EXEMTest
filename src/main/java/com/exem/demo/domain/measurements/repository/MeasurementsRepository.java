package com.exem.demo.domain.measurements.repository;

import com.exem.demo.domain.measurements.entity.Measurements;
import org.springframework.data.jpa.repository.JpaRepository;
public interface MeasurementsRepository extends JpaRepository<Measurements, Long> {
}
