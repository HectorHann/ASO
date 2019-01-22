package com.company.han;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Han on 2018/1/12.
 */
public class WriteDataToReport {
    public static void write(Map<String, String> data, String dstFilePath) {
//        System.out.println("Dst File = " + dstFilePath);
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(dstFilePath));
            HSSFCellStyle timeStyle = (HSSFCellStyle) workbook.createCellStyle();
            DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            DataFormat format = workbook.createDataFormat();
            timeStyle.setDataFormat(format.getFormat("yyyy/MM/dd"));


            Iterator<Sheet> iterator = workbook.sheetIterator();
            while (iterator.hasNext()) {
                HSSFSheet sheet = (HSSFSheet) iterator.next();


                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    HSSFRow row = sheet.getRow((short) i);
                    if (null == row) {
                        continue;
                    } else {
                        HSSFCell cell = row.getCell(0);
                        if (cell == null)
                            continue;
                        String time = df.format(cell.getDateCellValue());
                        cell = row.getCell(1);
                        if (cell == null) {
                            cell = row.createCell(1);
                        }
                        if (!StringUtils.isEmpty(data.get(time))) {
                            cell.setCellValue(Integer.valueOf(data.get(time)));
                            System.out.printf("Write " + time + "=" + data.get(time));
                        }

                    }
                }
                FileOutputStream out = null;
                try {
                    out = new FileOutputStream(dstFilePath);
                    workbook.write(out);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
