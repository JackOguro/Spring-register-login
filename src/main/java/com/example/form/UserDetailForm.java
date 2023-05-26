package com.example.form;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.model.MDepartment;
import com.example.model.TPurchase;

import lombok.Data;

@Data
public class UserDetailForm {
	
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
 	private MultipartFile acocountIcon;
	private Integer gender;
	private MDepartment mdepartment;
	private List<TPurchase> purchaseList;
}
