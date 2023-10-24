package com.spaghetti.earthquake.util;


import com.opencsv.CSVWriter;
import com.spaghetti.earthquake.model.Earthquake;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Component
public class FileGenerator {

    public static Optional<byte[]> generateCSVFile(List<? extends Earthquake> dataSet) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream, StandardCharsets.UTF_8);
                CSVWriter csvWriter = new CSVWriter(outputStreamWriter, ';', CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.DEFAULT_QUOTE_CHARACTER, CSVWriter.DEFAULT_LINE_END)) {
            String[] headers = {
                    "Date",
                    "Hour",
                    "Latitude",
                    "Longitude",
                    "Depth",
                    "(Size)ML",
                    "Location",
                    "Solution Quality"
            };
            csvWriter.writeNext(headers);

            for (Earthquake earthquake : dataSet) {
                String[] data = {
                        earthquake.date(),
                        earthquake.hour(),
                        String.valueOf(earthquake.latitude()),
                        String.valueOf(earthquake.longitude()),
                        String.valueOf(earthquake.size().ml()),
                        earthquake.location(),
                        earthquake.solutionQuality()
                };
                csvWriter.writeNext(data);
                csvWriter.flush();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Optional.of(byteArrayOutputStream.toByteArray());
    }
}
