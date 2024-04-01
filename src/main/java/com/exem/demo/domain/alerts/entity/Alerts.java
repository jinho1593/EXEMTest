package com.exem.demo.domain.alerts.entity;

import com.exem.demo.domain.measurements.entity.Measurements;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Alerts {

    @Id
    @Column(name = "alertId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alertId;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "measurement_id")
    private Measurements measurements;

    private String alertLevel; // 경보 단계

    private Integer alertGrade;// 경보 등급

    private LocalDateTime issuedTime; // 경보 발령 시간

    public Alerts(String alertLevel, Integer alertGrade, LocalDateTime issuedTime) {
        this.alertLevel = alertLevel;
        this.alertGrade = alertGrade;
        this.issuedTime = issuedTime;
    }
}
