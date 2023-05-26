package com.example.form;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class UserListForm {
		
	private String userId;
	private String phoneNumber;
	private String userName;
	@DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
	private Date birthday2;
	private Integer age;
	private Integer gender;
}
