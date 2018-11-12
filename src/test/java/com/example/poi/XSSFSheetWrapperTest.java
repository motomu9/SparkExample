package com.example.poi;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertAll;

class XSSFSheetWrapperTest {

  private static Workbook wb;

  @BeforeAll
  public static void setUpClass() throws IOException, InvalidFormatException {
    // テスト用のExcel
    File file = new File("./src/test/resources/POI/XSSFSheetWrapperTest.xlsx");
    wb = WorkbookFactory.create(file, null, true);
  }

  @Test
  @DisplayName("Cellの取得(取得可能範囲内)")
  public void getCell_001() {

    XSSFSheetWrapper ws = new XSSFSheetWrapper((XSSFSheet) wb.getSheet("getCellValue"));
    Cell res = ws.getCell(1, 0);

    assertAll(
        "res",
        () -> assertEquals(res.getRowIndex(), 1),
        () -> assertEquals(res.getColumnIndex(), 0));
  }

  @Test
  @DisplayName("Cellの取得(取得可能範囲外 Row)")
  public void getCell_002() {

    XSSFSheetWrapper ws = new XSSFSheetWrapper((XSSFSheet) wb.getSheet("getCellValue"));
    Cell res = ws.getCell(100, 0);

    assertNull(res);
  }

  @Test
  @DisplayName("Cellの取得(取得可能範囲外 Col)")
  public void getCell_003() {

    XSSFSheetWrapper ws = new XSSFSheetWrapper((XSSFSheet) wb.getSheet("getCellValue"));
    Cell res = ws.getCell(1, 100);

    assertNull(res);
  }

  @Test
  @DisplayName("NUMERIC(整数)")
  void getCellValue01() {

    XSSFSheetWrapper ws = new XSSFSheetWrapper((XSSFSheet) wb.getSheet("getCellValue"));
    String res = ws.getCellValue(ws.getCell(0, 1));
    assertEquals(res, "100");
  }

  @Test
  @DisplayName("STRING")
  void getCellValue02() {

    XSSFSheetWrapper ws = new XSSFSheetWrapper((XSSFSheet) wb.getSheet("getCellValue"));
    String res = ws.getCellValue(ws.getCell(1, 1));
    assertEquals(res, "TEST");
  }

  @Test
  @DisplayName("BLANK")
  void getCellValue03() {

    XSSFSheetWrapper ws = new XSSFSheetWrapper((XSSFSheet) wb.getSheet("getCellValue"));
    String res = ws.getCellValue(ws.getCell(2, 2));
    assertEquals(res, "");
  }

  @Test
  @DisplayName("BOOLEAN")
  void getCellValue04() {

    XSSFSheetWrapper ws = new XSSFSheetWrapper((XSSFSheet) wb.getSheet("getCellValue"));
    String res = ws.getCellValue(ws.getCell(3, 1));
    assertEquals(res, "TRUE");
  }

  @Test
  @DisplayName("ERROR")
  void getCellValue05() {

    XSSFSheetWrapper ws = new XSSFSheetWrapper((XSSFSheet) wb.getSheet("getCellValue"));
    String res = ws.getCellValue(ws.getCell(4, 1));
    assertEquals(res, "#REF!");
  }

  @Test
  @DisplayName("DATE")
  void getCellValue06() {

    XSSFSheetWrapper ws = new XSSFSheetWrapper((XSSFSheet) wb.getSheet("getCellValue"));
    String res = ws.getCellValue(ws.getCell(5, 1));
    assertEquals(res, "2018/11/12");
  }

  @Test
  @DisplayName("NUMERIC(小数)")
  void getCellValue07() {

    XSSFSheetWrapper ws = new XSSFSheetWrapper((XSSFSheet) wb.getSheet("getCellValue"));
    String res = ws.getCellValue(ws.getCell(6, 1));
    assertEquals(res, "12.3");
  }

  @Test
  @DisplayName("通常取得")
  void findCells_001() {
    Sheet ws = wb.getSheet("findCellsTest01");
    List<Cell> res = new XSSFSheetWrapper((XSSFSheet) ws).findCells("findCells");

    assertAll(
        "res",
        () -> assertEquals(res.size(), 2), // 2件ヒット
        () -> assertEquals(res.get(0).getRowIndex(), 7), // 8行目（0オリジン）
        () -> assertEquals(res.get(0).getColumnIndex(), 1), // B列
        () -> assertEquals(res.get(1).getRowIndex(), 21), // 22行目
        () -> assertEquals(res.get(1).getColumnIndex(), 8)); // I列
  }

  @Test
  @DisplayName("0件ヒット")
  void findCells_002() {
    Sheet ws = wb.getSheet("findCellsTest01");
    List<Cell> res = new XSSFSheetWrapper((XSSFSheet) ws).findCells("ABC");

    assertEquals(res.size(), 0);
  }

  @Test
  @DisplayName("Hitパターン")
  void findCell_001() {
    Sheet ws = wb.getSheet("findCellTest01");
    Cell res = new XSSFSheetWrapper((XSSFSheet) ws).findCell("findCell");

    assertAll(
        "res",
        () -> assertEquals(res.getRowIndex(), 7), // 8行目（0オリジン）
        () -> assertEquals(res.getColumnIndex(), 1)); // B列
  }

  @Test
  @DisplayName("Hitせず、null返却パターン")
  void findCell_002() {
    Sheet ws = wb.getSheet("findCellTest01");
    Cell res = new XSSFSheetWrapper((XSSFSheet) ws).findCell("ABC");

    assertNull(res);
  }

  @Test
  void findCellsWithRowNum() {
    Sheet ws = wb.getSheet("findCellsTest02");
    List<Cell> res = new XSSFSheetWrapper((XSSFSheet) ws).findCellsWithRowNum("findCells", 7);

    assertAll(
        "res",
        () -> assertEquals(res.size(), 3),
        () -> assertEquals(res.get(0).getRowIndex(), 7), // 8行目（0オリジン）
        () -> assertEquals(res.get(0).getColumnIndex(), 1), // B列
        () -> assertEquals(res.get(1).getRowIndex(), 7), // 8行目（0オリジン）
        () -> assertEquals(res.get(1).getColumnIndex(), 8), // I列
        () -> assertEquals(res.get(2).getRowIndex(), 7), // 8行目（0オリジン）
        () -> assertEquals(res.get(2).getColumnIndex(), 11)); // L列
  }

  @Test
  void findCellsWithColNum() {
    Sheet ws = wb.getSheet("findCellsTest02");
    List<Cell> res = new XSSFSheetWrapper((XSSFSheet) ws).findCellsWithColNum("findCells", 1);

    assertAll(
        "res",
        () -> assertEquals(res.size(), 2),
        () -> assertEquals(res.get(0).getRowIndex(), 7), // 8行目（0オリジン）
        () -> assertEquals(res.get(0).getColumnIndex(), 1), // B列
        () -> assertEquals(res.get(1).getRowIndex(), 16), // 17行目（0オリジン）
        () -> assertEquals(res.get(1).getColumnIndex(), 1)); // B列
  }

  @Test
  void equalsCellVal() {}
}
