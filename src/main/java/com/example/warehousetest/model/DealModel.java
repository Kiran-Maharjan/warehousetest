package com.example.warehousetest.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.math.BigInteger;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class DealModel {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Integer id;
	private String fromCurrencyIsoCode;
	private String toCurrencyIsoCode;
	private Date dealDate;
	private BigInteger amount;
	private String fileName;

}
