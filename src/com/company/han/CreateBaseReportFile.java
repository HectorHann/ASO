package com.company.han;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Han on 2018/1/12.
 */
public class CreateBaseReportFile {
    public static void create(int year, String filepath, String filename) {
        Workbook workbook = null;
        try {
            workbook = new HSSFWorkbook();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (workbook == null)
            return;

        HSSFCellStyle timeStyle = (HSSFCellStyle) workbook.createCellStyle();
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        DataFormat format = workbook.createDataFormat();
        timeStyle.setDataFormat(format.getFormat("yyyy/MM/dd"));

        for (int monthCount = 1; monthCount <= 12; monthCount++) {
            int dayMax = getMonthLastDay(year, monthCount);
            Sheet sheet = workbook.createSheet(String.valueOf(monthCount));

            int last_sumstart = 2;
            for (int rowNum = 1; rowNum <= dayMax; rowNum++) {
                Row row = sheet.createRow(rowNum);
                for (int i = 0; i < 4; i++) {
                    Cell cell = row.createCell(i);
                    switch (i) {
                        case 0:
                            cell.setCellValue(new Date(year - 1900, monthCount - 1, rowNum));
                            cell.setCellStyle(timeStyle);
                            break;
                        case 1:
//                            cell.setCellValue(rowNum);
                            break;
                        case 2:
                            if (rowNum > 1 && ((rowNum) % 7 == 0 || rowNum==dayMax)) {
                                cell.setCellValue("Weekly Total");
                            }
                            break;
                        case 3:
                            if (rowNum > 1 && ((rowNum) % 7 == 0 || rowNum==dayMax)) {
                                cell.setCellValue("Weekly Total");
                                int sumstart = rowNum - 6+1;
                                int sumend = rowNum+1;
                                if (rowNum ==dayMax){
                                    sumstart = last_sumstart+1;
                                }
                                cell.setCellFormula("SUM(B" + sumstart + ":B" + sumend + ")");
                                last_sumstart = sumend;
                            }
                            break;
                    }
                }
            }

            Row row0 = sheet.createRow(0);
            for (int i = 0; i < 4; i++) {
                Cell cell_1 = row0.createCell(i, Cell.CELL_TYPE_STRING);
                CellStyle style = workbook.createCellStyle();
                style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
                style.setFillPattern(CellStyle.SOLID_FOREGROUND);

                switch (i) {
                    case 0:
                        cell_1.setCellValue("Date");
                        break;
                    case 1:
                        cell_1.setCellValue("Daily Install");
                        break;
                    case 2:
                        cell_1.setCellValue("Total");
                        break;
                    case 3:
                        cell_1.setCellType(Cell.CELL_TYPE_STRING);
                        cell_1.setCellFormula("SUM(B:B)");
                        break;
                }
                cell_1.setCellStyle(style);
                sheet.setDefaultColumnWidth(15);
            }


            try {
                FileOutputStream outputStream = new FileOutputStream(filepath + filename);
                workbook.write(outputStream);
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                System.out.println("It cause Error on WRITTING excel workbook: ");
                e.printStackTrace();
            }
        }
    }


    private static int getMonthLastDay(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        return a.get(Calendar.DATE);
    }
}
