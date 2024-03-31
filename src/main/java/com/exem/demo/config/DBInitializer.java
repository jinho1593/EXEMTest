package com.exem.demo.config;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
@Getter
public class DBInitializer {
    @PostConstruct
    private void init() {
        log.debug("initializer 시작!");
        readCsv();
        log.debug("initializer 종료!");
    }

    private void readCsv() {
        String fileName = "2023년3월_서울시_미세먼지.csv";
        try (CSVReader reader = new CSVReader(new InputStreamReader(new ClassPathResource(fileName).getInputStream()))) {
            List<String[]> r = reader.readAll();
            r.forEach(x -> log.debug(String.join(", ", x)));
        } catch (IOException | CsvException e) {
            log.error("CSV 파일을 읽는 중 오류가 발생했습니다.", e);
        }
    }
}
