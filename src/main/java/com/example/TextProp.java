package com.example;

import com.example.util.io.CrLfReader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TextProp {

  public static List<String[]> prop() throws IOException {

    List<String[]> records = new ArrayList<>();

    Path path = Paths.get("D:\\Dev\\SparkExample\\upload\\改行問題.txt");

    try (CrLfReader reader =
        new CrLfReader(Files.newBufferedReader(path, StandardCharsets.UTF_8))) {
      do {
        String line = reader.next();
        if (line == null) break;
        records.add(line.split("\\t"));
      } while (true);
    }
    return records;
  }
}
