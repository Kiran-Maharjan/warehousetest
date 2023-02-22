package com.example.warehousetest.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@Entity
@Table(name = "accumulated_deal")
public class AccumulatedDeal implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;
	private String orderingCurrency;
	private BigInteger count ;

}
