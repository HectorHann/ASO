package com.company.han;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Han on 2018/1/11.
 */
public class ReadAndroidInstallCSVFile {
    public static ReadAndroidInstallCSVFile.AndroidCSVData read(String filepath) {
        ReadAndroidInstallCSVFile.AndroidCSVData androidCSVData = new ReadAndroidInstallCSVFile.AndroidCSVData();
        List<String[]> result = CSVUtils.read(filepath);
        List<String[]> realdata = (List<String[]>) result.subList(1, result.size());
        for (String[] strings : realdata) {
            String[] tmp =new String[2];
            for (int i=0;i<strings.length;i++){
                if (i==0){
                    tmp[i]=strings[i].replace("\u0000","").replace("-","/");
                }else if (i==2){
                    tmp[1]=strings[i].replace("\u0000","");
                }else {
                }
            }
            androidCSVData.data.put(tmp[0],tmp[1]);
        }

        return androidCSVData;
    }


    public static class AndroidCSVData {
        public Map<String,String> data =new HashMap<>();
    }
}
