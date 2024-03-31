package com.exem.demo.domain.stations.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Stations {
    @Id
    @Column(name = "stationId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stationId;

    private String name; // 측정소 이름

    public Stations(String name) {
        this.name = name;
    }
}
