package com.exem.demo.config;

import com.exem.demo.domain.alerts.entity.Alerts;
import com.exem.demo.domain.alerts.repository.AlertsRepository;
import com.exem.demo.domain.measurements.entity.Measurements;
import com.exem.demo.domain.measurements.repository.MeasurementsRepository;
import com.exem.demo.domain.stationMaintenance.entity.StationMaintenance;
import com.exem.demo.domain.stationMaintenance.repository.StationMaintenanceRepository;
import com.exem.demo.domain.stations.entity.Stations;
import com.exem.demo.domain.stations.repository.StationsRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DBInitializer {

    private final StationsRepository stationsRepository;
    private final StationMaintenanceRepository stationMaintenanceRepository;
    private final MeasurementsRepository measurementsRepository;
    private final AlertsRepository alertsRepository;

    @PostConstruct
    private void init() {
        log.debug("initializer 시작!");
        readCsv();
        log.debug("initializer 종료!");
    }

    private void readCsv() {
        String fileName = "2023년3월_서울시_미세먼지.csv";
        try (CSVReader reader = new CSVReader(new InputStreamReader(new ClassPathResource(fileName).getInputStream()))) {
            List<String[]> records = reader.readAll();
            for (int i = 1; i < records.size(); i++) {
                processRecord(records.get(i));
            }
        } catch (IOException | CsvException e) {
            log.error("CSV 파일을 읽는 중 오류가 발생했습니다.", e);
        }
    }


    private void processRecord(String[] record) {
        String measurementTimeStr = record[0];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
        LocalDateTime measurementTime = LocalDateTime.parse(measurementTimeStr, formatter);
        String stationName = record[1];
        String stationCode = record[2];

        int pm10 = 0;
        if (!record[3].isEmpty()) {
            pm10 = Integer.parseInt(record[3]);
        }

        int pm25 = 0;
        if (!record[4].isEmpty()) {
            pm25 = Integer.parseInt(record[4]);
        }


        Stations station = stationsRepository.findByName(stationName)
                .orElseGet(() -> stationsRepository.save(new Stations(stationName)));

        int timeCount = 0;

        // 초미세먼지 경보
        if (pm25 > 150) {
            timeCount++;
            if (timeCount >= 2) {
                String alertType;
                int alertLevel;
                alertType = "초미세먼지 경보";
                alertLevel = 1;
            }
        }

        // 미세먼지 경보
        if (pm10 > 300) {
            timeCount++;
            if (timeCount >= 2) {
                String alertType;
                int alertLevel;
                alertType = "미세먼지 경보";
                alertLevel = 2;
            }
        }

        // 초미세먼지 주의보
        if (pm25 > 75) {
            timeCount++;
            if (timeCount >= 2) {
                String alertType;
                int alertLevel;
                alertType = "초미세먼지 주의보";
                alertLevel = 3;
            }
        }

         // 미세먼지 주의보
        if (pm10 > 150) {
            timeCount++; // PM10 값이 150을 초과하면 timeCount 증가
            if (timeCount >= 2) {
                String alertType;
                int alertLevel;
                alertType = "미세먼지 주의보";
                alertLevel = 4;
            }
        }

        // timeCount가 1 이상이면 경보 발령
        if (timeCount >= 2) {
            String alertType;
            int alertLevel;

            // 초미세먼지 경보
            if (pm25 > 150) {
                alertType = "초미세먼지 경보";
                alertLevel = 1;
            }
            // 미세먼지 경보
            else if (pm10 > 300) {
                alertType = "미세먼지 경보";
                alertLevel = 2;
            }
            // 초미세먼지 주의보
            else if (pm25 > 75) {
                alertType = "초미세먼지 주의보";
                alertLevel = 3;
            }
            // 미세먼지 주의보
            else if (pm10 > 150) {
                alertType = "미세먼지 주의보";
                alertLevel = 4;
            } else {
                // 다른 경우는 없음
                return;
            }

            // 경보 발령 로직
            Measurements measurements = new Measurements(station, measurementTime, pm10, pm25);
            measurementsRepository.save(measurements);
            Alerts alerts = new Alerts(alertType, alertLevel, measurementTime);
            alertsRepository.save(alerts);
        }

        // timeCount를 초기화
        timeCount = 0;

        if (pm10 == 0 || pm25 == 0) {
            StationMaintenance maintenance = new StationMaintenance(station, measurementTime);
            stationMaintenanceRepository.save(maintenance);
        }
    }
}
