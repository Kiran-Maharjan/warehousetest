package com.example.warehousetest.controller;

import com.example.warehousetest.csv.CSVRecord;
import com.example.warehousetest.exceptionhandlers.exceptions.EntityAlreadyExistsException;
import com.example.warehousetest.exceptionhandlers.exceptions.InvalidDataException;
import com.example.warehousetest.service.IFileService;
import com.opencsv.CSVReader;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@RestController
public class UploadController {

    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    @Autowired
    IFileService service;

    @GetMapping(value = "/test")
    public String test() {
        return "test";
    }

    @PostMapping(value = "/uploadFile")
    public String uploadFile(
            ModelMap model,
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes) {


        if (file.isEmpty()) {
            //-- Exception handling
            throw new InvalidDataException("The provided record doesn't exists, file empty");

//            logger.info("Validation failed file is empty");
//            return "Validation failed file is empty";
        } else if (service.isFileExist(file.getOriginalFilename())) {
            //-- Exception handling
            throw new EntityAlreadyExistsException("The provided record already exists");

//            logger.info("File already exist!!");
//            return "File already exist!!";
        }
        String fileName = file.getOriginalFilename();
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        File dir = new File(rootPath + File.separator + "uploaded");
        if (!dir.exists()) {
            dir.mkdirs();
        }


        File serverFile = new File(dir.getAbsolutePath() + File.separator + (fileName));


        try {
            try (InputStream is = file.getInputStream();
                 BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile))) {
                int i;
                //write file to server
                while ((i = is.read()) != -1) {
                    stream.write(i);
                }
                stream.flush();
            }
        } catch (IOException ex) {
            logger.error("Exception: ", ex);
            return "Exception: " + ex;
        }

        try {
            logger.info("Reading processed to CSV file");
            List<CSVRecord> validDeals = new ArrayList<>();
            List<CSVRecord> inValidDeals = new ArrayList<>();
            for (String[] line : readCSVFile(serverFile, fileName)) {
                CSVRecord csvRecord = extractDeal(line);
                csvRecord.setFileName(fileName);
                if (StringUtils.isEmpty(csvRecord.getFromCurrencyIsoCode())) {
                    csvRecord.setReason("from currency is empty");
                    inValidDeals.add(csvRecord);
                } else if (StringUtils.isEmpty(csvRecord.getToCurrencyIsoCode())) {
                    csvRecord.setReason("to currency is empty");
                    inValidDeals.add(csvRecord);
                } else if (StringUtils.isEmpty(csvRecord.getDealDate())) {
                    csvRecord.setReason("deal date is empty");
                    inValidDeals.add(csvRecord);
                } else if (StringUtils.isEmpty(csvRecord.getAmount())) {
                    csvRecord.setReason(" amount is empty");
                    inValidDeals.add(csvRecord);
                } else {
                    validDeals.add(csvRecord);

                }

            }
            if (validDeals.size() > 0)
                service.saveValidDeal(validDeals);
            if (inValidDeals.size() > 0)
                service.saveInValidDeal(inValidDeals);

        } catch (Exception e) {
            logger.error("Exception: ", e);
        }
        logger.info("Completed successfully!!!");
        return "Completed successfully!!!";
    }

    CSVRecord extractDeal(String[] line) {

        CSVRecord dealModel = new CSVRecord();
        dealModel.setToCurrencyIsoCode(line[0]);
        dealModel.setFromCurrencyIsoCode(line[1]);

        try {
            dealModel.setDealDate(formatter.parse(line[2]));
        } catch (ParseException e) {
        }

        dealModel.setAmount(new BigInteger(line[3]));
        return dealModel;
    }

    List<String[]> readCSVFile(File serverFile, String fileName) {
        List<String[]> lines = null;
        try {
            logger.info(" reading CSV file");
            FileReader fileReader = new FileReader(serverFile);
            CSVReader reader = new CSVReader(fileReader, ',');
            lines = reader.readAll();

        } catch (IOException e) {
            logger.error("Exception:", e);
        }
        return lines;
    }


}