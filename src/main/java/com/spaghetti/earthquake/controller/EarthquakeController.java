package com.spaghetti.earthquake.controller;

import com.spaghetti.earthquake.model.Earthquake;
import com.spaghetti.earthquake.service.EarthquakeService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/earthquakes")
public class EarthquakeController {
    private EarthquakeService  earthquakeService;

    public EarthquakeController(EarthquakeService earthquakeService) {
        this.earthquakeService = earthquakeService;
    }

    @GetMapping("/getAll")
    public List<Earthquake> getAll(){
        return earthquakeService.getAll();
    }

    @GetMapping("/getByDate")
    public List<Earthquake> getByDate(@RequestParam String date){
        return earthquakeService.getByDate(date);
    }

    @GetMapping("/getBySize/{size}")
    public List<Earthquake> getBySize(@PathVariable double size){
        return earthquakeService.getBySize(size);
    }

    @GetMapping("/getByLocation/{location}")
    public List<Earthquake> getByLocation(@PathVariable String location){
        return earthquakeService.getByLocation(location);
    }

    @GetMapping("/getByDateAndSize")
    public List<Earthquake> getByDateAndSize(@RequestParam("date") String date ,@RequestParam("size") double size){
        return earthquakeService.getByDateAndSize(date, size);
    }
    @GetMapping("/getByDateAndLocation")
    public List<Earthquake> getByDateAndLocation(@RequestParam("date") String date ,@RequestParam("location") String location){
        return earthquakeService.getByDateAndLocation(date, location);
    }

    @GetMapping("/download-csv")
    public ResponseEntity<byte[]> downloadCSV() {
        byte[] csvBytes = earthquakeService.generateCsvFile();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "earthquake.csv");

        return ResponseEntity.ok()
                .headers(headers)
                .body(csvBytes);
    }
}
