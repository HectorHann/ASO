package com.company.han;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Han on 2018/1/11.
 */

public class CSVUtils {




    public static List<String[]> read(String filepath) {
        List<String[]> result = new ArrayList<>();
        try {
            FileReader fReader = new FileReader(new File(filepath));
            CSVReader csvReader = new CSVReader(fReader);
            result = csvReader.readAll();
            csvReader.close();
        } catch (IOException e) {
        }
        return result;
    }
}

