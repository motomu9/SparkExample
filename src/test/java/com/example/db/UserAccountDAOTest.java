package com.example.db;

import com.example.UseDBTestBase;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserAccountDAOTest extends UseDBTestBase {

    @BeforeAll
    public static void beforeClass() {

        backup("D:\\Dev\\SparkExample\\src\\test\\detaset\\bk\\backup.xlsx", "UserAccount");

    }

    @AfterAll
    public static void afterAll() {
        restore();
    }

    @Test
    void getAll() {

        deleteAndInsertData("D:\\Dev\\SparkExample\\src\\test\\detaset\\sampletb.xlsx");

        // テストコード
        assertEquals(new UserAccountDAO().getAll().size(), 2);
    }

}
