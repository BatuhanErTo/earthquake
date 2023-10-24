package com.spaghetti.earthquake.service;

import com.spaghetti.earthquake.model.Earthquake;
import com.spaghetti.earthquake.util.DataParser;
import com.spaghetti.earthquake.util.FileGenerator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EarthquakeService {
    private DataParser dataParser;

    public EarthquakeService(DataParser dataParser) {
        this.dataParser = dataParser;
    }

    private List<Earthquake> getDataSet(){
        return dataParser.getEarthquakeData();
    }

    public List<Earthquake> getAll(){
        return getDataSet();
    }

    public List<Earthquake> getByDate(String date) {
        return getDataSet().stream().filter(earthquake -> earthquake.date().equals(date))
                .collect(Collectors.toList());
    }

    public List<Earthquake> getBySize(double size) {
        return getDataSet().stream().filter(earthquake -> sizeChecker(size, earthquake.size().ml()))
                .collect(Collectors.toList());
    }

    public List<Earthquake> getByLocation(String location) {
        return getDataSet().stream().filter(earthquake -> earthquake.location().equals(location))
                .collect(Collectors.toList());
    }

    public List<Earthquake> getByDateAndSize(String date, double size) {
        return getDataSet().stream().filter(earthquake -> earthquake.date().equals(date) &&
                        sizeChecker(size, earthquake.size().ml()))
                .collect(Collectors.toList());
    }

    private boolean sizeChecker(double size, String data) {
        return size == Double.parseDouble(data);
    }

    public List<Earthquake> getByDateAndLocation(String date, String location) {
        return getDataSet().stream().filter(earthquake -> earthquake.date().equals(date) &&
                        earthquake.location().equals(location))
                .collect(Collectors.toList());
    }

    public byte[] generateCsvFile(){
        Optional<byte[]> csvFile = FileGenerator.generateCSVFile(getDataSet());
        if (csvFile.isPresent()){
            return csvFile.get();
        }else {
            throw new NoSuchElementException("File is not created");
        }
    }
}
