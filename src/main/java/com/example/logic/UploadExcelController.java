package com.example.logic;

import com.example.enums.UploadFileType;

public class UploadExcelController {

  public void importExcelFile(UploadFileType uploadedFileType, String fileName) {

    switch (uploadedFileType) {
      case Master_File1:
        new ImportMaster_File1().importFile(fileName);
        break;
      case Master_File2:
        new ImportMaster_File2().importFile(fileName);
        break;
      case Master_File3:
        new ImportMaster_File3().importFile(fileName);
        break;
      case Tran_File1:
        new ImportTran_File1().importFile(fileName);
        break;
      case Tran_File2:
        new ImportTran_File2().importFile(fileName);
        break;
    }

    System.out.println("★fileName=" + fileName);
  }
}
