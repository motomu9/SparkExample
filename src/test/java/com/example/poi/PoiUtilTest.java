package com.example.poi;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertAll;

class PoiUtilTest {

    private static Workbook wb;

    @BeforeAll
    public static void setUpClass() throws IOException, InvalidFormatException {
        // テスト用のExcel
        File file = new File("./src/test/resources/POI/POIUtilTest.xlsx");
        wb = WorkbookFactory.create(file, null, true);
    }

    @Test
    @DisplayName("通常取得")
    void findCells_001() {
        Sheet ws = wb.getSheet("findCellsTest01");
        List<Cell> res = PoiUtil.findCells(ws, "findCells");

        assertAll("res",
                () -> assertEquals(res.size(), 2),  // 2件ヒット
                () -> assertEquals(res.get(0).getRowIndex(), 7),    // 8行目（0オリジン）
                () -> assertEquals(res.get(0).getColumnIndex(), 1), // B列
                () -> assertEquals(res.get(1).getRowIndex(), 21),   // 22行目
                () -> assertEquals(res.get(1).getColumnIndex(), 8));   // I列
    }

    @Test
    @DisplayName("0件ヒット")
    void findCells_002() {
        Sheet ws = wb.getSheet("findCellsTest01");
        List<Cell> res = PoiUtil.findCells(ws, "ABC");

        assertEquals(res.size(), 0);
    }

    @Test
    @DisplayName("Hitパターン")
    void findCell_001() {
        Sheet ws = wb.getSheet("findCellTest01");
        Cell res = PoiUtil.findCell(ws, "findCell");

        assertAll("res",
                () -> assertEquals(res.getRowIndex(), 7),   // 8行目（0オリジン）
                () -> assertEquals(res.getColumnIndex(), 1)); // B列
    }

    @Test
    @DisplayName("Hitせず、null返却パターン")
    void findCell_002() {
        Sheet ws = wb.getSheet("findCellTest01");
        Cell res = PoiUtil.findCell(ws, "ABC");

        assertNull(res);
    }

}