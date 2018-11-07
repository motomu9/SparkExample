package com.example.poi;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;

public class PioReadExample {
    public static void main(String... args) throws IOException, InvalidFormatException {

        File file = new File("D:\\Dev\\SparkExample\\src\\main\\resources\\POI\\Sample.xlsx");
        Workbook workbook = WorkbookFactory.create(file);

        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            Sheet sheet = workbook.getSheetAt(i);
            System.out.println("sheetName=" + sheet.getSheetName());

            Row row = sheet.getRow(0);
            if (row != null) {
                Cell cell = row.getCell(0);
                if (cell != null) {
                    switch (cell.getCellTypeEnum()) {
                        case NUMERIC:
                            System.out.println(cell.getNumericCellValue());
                            break;
                        case STRING:
                            System.out.println(cell.getStringCellValue());
                            break;
                        default:
                            System.out.println("cellType=" + cell.getCellTypeEnum());
                            break;
                    }
                }
            }
        }
    }
}
