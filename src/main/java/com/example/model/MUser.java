package com.example.model;

import java.util.Date;
import java.util.List;

import com.example.util.Authority;

import lombok.Data;

@Data
public class MUser {

	private String userId; 
	private String phoneNumber; 
	private Integer postalNumber1;
	private Integer postalNumber2;
	private String address;
	private String userName;
	private String password;
	private Date birthday1;
	private Date birthday2;
	private Integer age;
	private byte[] accountIcon1;
	private String accountIcon2;
	private Integer gender;
	private Integer departmentId;
	private Authority authority;
	private MDepartment mdepartment;
	private List<TPurchase> purchaseList; // パターン2の時は使用しない
}
