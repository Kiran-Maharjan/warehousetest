package com.example.warehousetest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
@Data
@Entity
@Table(name = "valid_deal")
public class ValidDeal extends  DealModel implements java.io.Serializable {

	
	private static final long serialVersionUID = 1L;

}
