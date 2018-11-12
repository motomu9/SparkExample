package com.example.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PoiUtil {

  /**
   * findCells 文字列の検索を行う。 検索文字列が見つからなかった場合はsize=0のリストを返却する。 検索文字列はトリムされてから検索される。
   *
   * @param sheet 検索対象シート
   * @param content 検索文字列
   * @return ヒットしたセルList
   */
  public static List<Cell> findCells(@NotNull Sheet sheet, String content) {
    List<Cell> list = new ArrayList<>();
    for (Row row : sheet) {
      for (Cell cell : row) {
        if (cell.getCellTypeEnum() == CellType.STRING) {
          if (cell.getRichStringCellValue().getString().trim().equals(content)) {
            list.add(cell);
          }
        }
      }
    }
    return list;
  }

  /**
   * findCell 文字列の検索を行う。 最初に摘出されたCellを返却する。 検索文字列が見つからなかった場合はnullを返却する。 検索文字列はトリムされてから検索される。
   *
   * @param sheet 検索対象シート
   * @param content 検索文字列
   * @return ヒットしたセル
   */
  public static Cell findCell(@NotNull Sheet sheet, String content) {
    for (Row row : sheet) {
      for (Cell cell : row) {
        if (cell.getCellTypeEnum() == CellType.STRING) {
          if (cell.getRichStringCellValue().getString().trim().equals(content)) {
            return cell;
          }
        }
      }
    }
    return null;
  }


}
