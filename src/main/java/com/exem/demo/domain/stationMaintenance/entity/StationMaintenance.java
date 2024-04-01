package com.exem.demo.domain.stationMaintenance.entity;

import com.exem.demo.domain.stations.entity.Stations;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class StationMaintenance {
    @Id
    @Column(name = "maintenanceId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long maintenanceId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "station_id")
    private Stations stations;

    private LocalDateTime maintenanceDate; // 점검 날짜

    public StationMaintenance(Stations stations, LocalDateTime maintenanceDate) {
        this.stations = stations;
        this.maintenanceDate = maintenanceDate;
    }
}
