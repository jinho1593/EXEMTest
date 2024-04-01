package com.exem.demo.domain.stations.repository;

import com.exem.demo.domain.stations.entity.Stations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StationsRepository extends JpaRepository<Stations, Long> {

    Optional<Stations> findByName(String stationName);
}
