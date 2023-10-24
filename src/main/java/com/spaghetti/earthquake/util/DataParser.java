package com.spaghetti.earthquake.util;

import com.spaghetti.earthquake.constans.UrlSource;
import com.spaghetti.earthquake.model.Earthquake;
import com.spaghetti.earthquake.model.Size;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DataParser {
    public  List<Earthquake> getEarthquakeData(){
        List<Earthquake> dataSet = new ArrayList<>();
        String url = UrlSource.urlOfKandilli;
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Element element = doc.select("pre").first();
        String[] lines = element.text().split("\n");

        for (int i = 6; i < lines.length; i++) {
            String[] data = lines[i].split("\\s+");
            String location = String.join(" ",Arrays.copyOfRange(data,8,data.length-1));
            Earthquake earthquake = new Earthquake(data[0],
                    data[1],
                    Double.parseDouble(data[2]),
                    Double.parseDouble(data[3]),
                    Double.parseDouble(data[4]),
                    new Size(data[5],data[6],data[7]),
                    location,
                    data[data.length-1]);
            dataSet.add(earthquake);
        }
        return dataSet;
    }
}
