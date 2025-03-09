package com.swissre.employee.serviceImpl;

import com.swissre.employee.service.Reader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CSVReader implements Reader {


    @Value("${csv.file.path}")
    private String csvFilePath;

    /**
     * Method to read CSV file available in CSV .
     * Please replace file / update data available in resources folder(employees.csv)
     */
    @Override
    public List<String[]> parseCsv() {
        List<String[]> dataList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                dataList.add(data);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file: " + e.getMessage());
        }
        return dataList;
    }
}
