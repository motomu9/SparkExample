package com.example;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.SortedTable;
import org.dbunit.dataset.excel.XlsDataSet;
import org.dbunit.dataset.excel.XlsDataSetWriter;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class UseDBTestBase {

    private static IDatabaseConnection dbconn = null;
    private static IDataSet dataset = null;
    private static String BackupFilePath = null;

    @BeforeAll
    public static void beforeAll() throws Exception {

        // DBコネクション取得
        Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433", "TEST_USER", "TEST");
        dbconn = new DatabaseConnection(conn);

    }

    @BeforeEach
    public void beforeEach() {
        // datasetは初期化する
        dataset = null;
    }

    @AfterAll
    static void afterAll() throws SQLException {

        dbconn.close();

    }

    /**
     * Delete And Insert
     */
    public void deleteAndInsertData(String dataPath) {
        setData(dataPath, false);
    }

    /**
     * Only Delete
     */
    public void deleteData(String dataPath) {
        setData(dataPath, true);
    }

    /**
     * データの設定を行う
     */
    private void setData(String dataPath, boolean flgDeleteOnly) {

        // null or 空文字なら処理なし
        if (dataPath == null || "".equals(dataPath)) return;

        try {
            // Excel用データセット作成
            dataset = new XlsDataSet(new File(dataPath));

            // 「削除のみ」フラグ
            if (flgDeleteOnly) {
                // データの全削除
                DatabaseOperation.DELETE_ALL.execute(dbconn, dataset);
            } else {
                // データの挿入
                DatabaseOperation.CLEAN_INSERT.execute(dbconn, dataset);
            }

        } catch (DatabaseUnitException | SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * データのバックアップ
     */
    public static void backup(String backupFilePath, String... backupTableName) {
        try {
            // バックアップを取りたいテーブルを列挙
            QueryDataSet partialDataSet = new QueryDataSet(dbconn);

            // テーブルを登録する
            for (String tbl : backupTableName) {
                partialDataSet.addTable(tbl);
            }

            XlsDataSetWriter writer = new XlsDataSetWriter() {
                @Override
                public Workbook createWorkbook() {
                    return new XSSFWorkbook();
                }
            };
            writer.write(partialDataSet, new FileOutputStream(backupFilePath));
            BackupFilePath = backupFilePath;

        } catch (IOException | DataSetException e) {
            e.printStackTrace();
        }
    }

    /**
     * データのリストア
     */
    public static void restore() {

        if (BackupFilePath == null || "".equals(BackupFilePath)) return;

        // setUp()で生成したバックアップファイルを引数に再度データをinsert
        try {
            IDataSet dataSet = new XlsDataSet(new FileInputStream(BackupFilePath));
            DatabaseOperation.CLEAN_INSERT.execute(dbconn, dataSet);
        } catch (DatabaseUnitException | SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void compareExcel() throws IOException, DataSetException, SQLException {
        String[] sort = {"カラム名1", "カラム名2"};
        String[] exclude = {"カラム名3"};

        FileInputStream fis = new FileInputStream("insert.xlsx");
        IDataSet expectDataSet = new XlsDataSet(fis);
        ITable expect = expectDataSet.getTable("insert.xlsxのシート名");

        // 比較対象外のカラム設定
        expect = DefaultColumnFilter.excludedColumnsTable(expect, exclude);
        // 指定したカラムでソート
        expect = new SortedTable(expect, sort);
        IDataSet dataSet = dbconn.createDataSet();
        ITable actual = dataSet.getTable("シート名");

        // 比較対象外のカラム設定
        actual = DefaultColumnFilter.excludedColumnsTable(actual, exclude);
        // 指定したカラムでソート
        actual = new SortedTable(actual, sort);
        // 検証 Assertion.assertEquals(actual,expect);
    }

}
