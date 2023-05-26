package com.example.model;

import java.util.List;

import lombok.Data;

@Data
public class MProduct {

	private Integer productId;
	private String productName;
	private Integer productPrice;
	private List<TPurchase> purchaseList;
}
