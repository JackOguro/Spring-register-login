package com.example.model;

import java.util.Date;

import lombok.Data;

@Data
public class TPurchase {

	private Integer orderId;
	private String userId;
	private Integer productId;
	private Integer count;
	private Date purchaseDate;
	private MProduct product;
	private MUser user; 
}
