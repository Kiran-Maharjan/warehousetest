package com.example.warehousetest.service.impl;

import com.example.warehousetest.dao.IFileUploadDAO;
import com.example.warehousetest.model.AccumulatedDeal;
import com.example.warehousetest.csv.CSVRecord;
import com.example.warehousetest.model.InValidDeal;
import com.example.warehousetest.model.ValidDeal;
import com.example.warehousetest.service.IFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileService implements IFileService {

	private static Logger logger = LoggerFactory.getLogger(FileService.class);

	@Autowired
	IFileUploadDAO dao;

	@Override
	public void saveValidDeal(List<CSVRecord> dealDetails) {
		logger.info("Process saving valid deal: "+ dealDetails.size());
		List<ValidDeal> validDeals = new ArrayList<>();
		Map accumulativeValues = new HashMap<>(); 
		for(CSVRecord deal:dealDetails){
			ValidDeal target = new ValidDeal();
			if(accumulativeValues.containsKey(deal.getFromCurrencyIsoCode())){
				int value = Integer.parseInt(String.valueOf((accumulativeValues.get(deal.getFromCurrencyIsoCode()))));
				accumulativeValues.put(deal.getFromCurrencyIsoCode(), ++value);
			}
			else{
				accumulativeValues.put(deal.getFromCurrencyIsoCode(), 1);
			}
			BeanUtils.copyProperties(deal, target);
			validDeals.add(target);
		}
		List<AccumulatedDeal> accumulatedDeals = new ArrayList<>();
		for (Object key : accumulativeValues.keySet()) {
			AccumulatedDeal accumulatedDeal = new AccumulatedDeal();
			accumulatedDeal.setCount(new BigInteger(String.valueOf(accumulativeValues.get(key))));
			accumulatedDeal.setOrderingCurrency(key.toString());
			accumulatedDeals.add(accumulatedDeal);
		    System.out.println("Key = " + key + " - " + accumulativeValues.get(key));
		}
		dao.bulkValidSave(validDeals, accumulatedDeals);
	}

	@Override
	public void saveInValidDeal(List<CSVRecord> dealDetails) {

		logger.info("Process saving invalid deal: "+ dealDetails.size());
		List<InValidDeal> inValidDeals = new ArrayList<>();
		
		for(CSVRecord deal:dealDetails){
			InValidDeal target = new InValidDeal();
			BeanUtils.copyProperties(deal, target);
			inValidDeals.add(target);
		}
		dao.bulkInvalidSave(inValidDeals);
	}

	@Override
	public boolean isFileExist(String fileName) {
		return dao.fileExists(fileName);
	}

}
