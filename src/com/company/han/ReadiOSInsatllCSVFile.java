package com.company.han;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Han on 2018/1/11.
 */
public class ReadiOSInsatllCSVFile {
    public static IOSCSVData read(String filepath) {

        DateFormat df = new SimpleDateFormat("yyyy/M/d");
        DateFormat dfn = new SimpleDateFormat("yyyy/MM/dd");

        IOSCSVData iOSCSVData = new IOSCSVData();
        List<String[]> result = CSVUtils.read(filepath);
        List<String[]> realdata = (List<String[]>) result.subList(5, result.size());
        try {
            for (String[] strings : realdata) {
                String[] tmp = new String[strings.length];
                for (int i = 0; i < strings.length; i++) {
                    if (i == 1) {
                        tmp[1] = strings[1].replace(".0", "");
                    } else {
                        tmp[i] = strings[i];
                        tmp[i] = dfn.format(df.parse(tmp[i]));
                    }
                }
                iOSCSVData.data.put(tmp[0], tmp[1]);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return iOSCSVData;
    }


    public static class IOSCSVData {
        public Map<String, String> data = new HashMap<>();
    }
}



