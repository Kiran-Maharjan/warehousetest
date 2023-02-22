package com.example.warehousetest.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invalid_deal")
public class InValidDeal extends  DealModel implements java.io.Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private String reason;

}
