package com.exem.demo.domain.measurements.entity;

import com.exem.demo.domain.stations.entity.Stations;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Measurements {

    @Id
    @Column(name = "measurementId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long measurementId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "station_id")
    private Stations stations;


    private LocalDateTime measurementTime; // 측정 날짜 및 시간

    private Integer pm10; // 미세 먼지 농도

    private Integer pm25; // 초미세 먼지 농도


    public Measurements(Stations stations, LocalDateTime measurementTime, Integer pm10, Integer pm25) {
        this.stations = stations;
        this.measurementTime = measurementTime;
        this.pm10 = pm10;
        this.pm25 = pm25;
    }
}
