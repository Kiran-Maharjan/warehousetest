package com.example.warehousetest.csv;

import lombok.*;

import java.math.BigInteger;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CSVRecord {

	private Integer id;
	private String fromCurrencyIsoCode;
	private String toCurrencyIsoCode;
	private Date dealDate;
	private BigInteger amount;
	private String reason;
	private String fileName;
}
