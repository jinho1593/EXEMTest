package com.exem.demo.domain.measurements.entity;

import com.exem.demo.domain.stations.entity.Stations;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Measurements {

    @Id
    @Column(name = "measurementId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long measurementId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "station_id")
    private Stations stations;

    private String dateTime; // 측정 날짜 및 시간

    private Integer stationCode; // 측정소 코드

    private Integer pm10; // 미세 먼지 농도

    private Integer pm2_5; // 초미세 먼지 농도

    public Measurements(Stations stations, String dateTime, Integer stationCode, Integer pm10, Integer pm2_5) {
        this.stations = stations;
        this.dateTime = dateTime;
        this.stationCode = stationCode;
        this.pm10 = pm10;
        this.pm2_5 = pm2_5;
    }
}
