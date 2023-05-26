package com.example.form;

import com.example.model.MProduct;

import lombok.Data;

@Data
public class PurchaseForm {

	private Integer count;
	private Data purchaseDate;
	private MProduct product;
}
