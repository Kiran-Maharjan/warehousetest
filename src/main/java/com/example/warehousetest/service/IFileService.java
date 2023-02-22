package com.example.warehousetest.service;

import com.example.warehousetest.csv.CSVRecord;

import java.util.List;
public interface IFileService {

	boolean isFileExist(String fileName);

	void saveValidDeal(List<CSVRecord> dealDetails);

	void saveInValidDeal(List<CSVRecord> dealDetails);

}
