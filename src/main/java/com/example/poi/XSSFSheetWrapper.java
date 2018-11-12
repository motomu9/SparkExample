package com.example.poi;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.*;

public class XSSFSheetWrapper extends XSSFSheet {

  private Sheet sheet;

  public XSSFSheetWrapper(XSSFSheet sheet) {
    super(sheet.getPackagePart());
    this.sheet = sheet;
  }

  /**
   * 指定されたアドレスのCellを返却する 取得範囲外のCellを指定した場合はnullを返却する。
   *
   * @param r
   * @param c
   * @return 指定されたCellオブジェクト
   */
  public Cell getCell(int r, int c) {

    if (this.sheet.getLastRowNum() < r) return null;
    Row row = this.sheet.getRow(r);

    if (row.getLastCellNum() < c) return null;
    Cell cell = row.getCell(c);

    return cell;
  }

  /**
   * findCells 文字列の検索を行う。 検索文字列が見つからなかった場合はsize=0のリストを返却する。 検索文字列はトリムされてから検索される。
   *
   * @param content 検索文字列
   * @return ヒットしたセルList
   */
  public List<Cell> findCells(String content) {
    List<Cell> list = new ArrayList<>();
    for (Row row : this.sheet) {
      for (Cell cell : row) {
        if (equalsCellVal(cell, content)) {
          list.add(cell);
        }
      }
    }
    return list;
  }

  /**
   * @param content 検索文字列
   * @param r 行指定
   * @return ヒットしたセルList
   */
  public List<Cell> findCellsWithRowNum(String content, int r) {
    List<Cell> list = new ArrayList<>();

    // マイナス指定の場合は空のリストを返却
    if (r < 0) return list;

    // 行指定あり
    for (Cell cell : this.sheet.getRow(r)) {
      if (equalsCellVal(cell, content)) {
        list.add(cell);
      }
    }
    return list;
  }

  /**
   * @param content 検索文字列
   * @param c 列指定
   * @return ヒットしたセルList
   */
  public List<Cell> findCellsWithColNum(String content, int c) {
    List<Cell> list = new ArrayList<>();

    // マイナス指定の場合は空のリストを返却
    if (c < 0) return list;
    for (Row row : this.sheet) {
      Cell cell = row.getCell(c);
      if (equalsCellVal(cell, content)) {
        list.add(cell);
      }
    }
    return list;
  }

  /**
   * findCell 文字列の検索を行う。 最初に摘出されたCellを返却する。 検索文字列が見つからなかった場合はnullを返却する。 検索文字列はトリムされてから検索される。
   *
   * @param content 検索文字列
   * @return ヒットしたセル
   */
  public Cell findCell(String content) {
    for (Row row : this.sheet) {
      for (Cell cell : row) {
        if (equalsCellVal(cell, content)) {
          return cell;
        }
      }
    }
    return null;
  }

  /**
   * セルの値を取得する。 数式は計算後の値となる。 セル値がnullの場合はnullを返却する。
   *
   * @param cell
   * @return セルの値
   */
  public String getCellValue(Cell cell) {

    // 引数がnullの場合は空文字を返却
    if (Objects.isNull(cell)) return "";

    String val = "";

    switch (cell.getCellTypeEnum()) {

      case NUMERIC:

        // 日付チェック
        if (DateUtil.isCellDateFormatted(cell)) {
          // 日付の場合
          Calendar cal = DateUtil.getJavaCalendar(cell.getNumericCellValue());
          val = new SimpleDateFormat("yyyy/MM/dd").format(cal.getTime());
        } else {
          // 数値
          DataFormatter formatter = new DataFormatter();
          String retValue = formatter.formatCellValue(cell);
          if (retValue.endsWith("_ ")) {
            retValue = retValue.substring(0, retValue.length() - 2);
          }
          val =  retValue;
        }
        break;

      case STRING:
        val = cell.getRichStringCellValue().getString();
        break;

      case FORMULA:
        Workbook workbook = cell.getSheet().getWorkbook();
        Cell cellTmp = workbook.getCreationHelper().createFormulaEvaluator().evaluateInCell(cell);
        val = getCellValue(cellTmp);
        break;

      case BLANK:
        val = "";
        break;

      case BOOLEAN:
        val = String.valueOf(cell.getBooleanCellValue()).toUpperCase();
        break;

      case ERROR:
        byte errorCode = cell.getErrorCellValue();
        FormulaError error = FormulaError.forInt(errorCode);
        val = error.getString();
        break;

      default:
        System.out.println("cellType=" + cell.getCellTypeEnum());
        break;
    }
    return val;
  }

  /**
   * 　CellがString以外の書式でも文字列を比較する。
   *
   * @param cell チェック対象のCellオブジェクト
   * @param content 検索文字列
   * @return Hit？
   */
  public boolean equalsCellVal(Cell cell, @NotNull String content) {
    return content.trim().equals(getCellValue(cell).trim());
  }
}
