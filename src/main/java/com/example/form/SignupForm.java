package com.example.form;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SignupForm {
	
	@NotBlank(groups = ValidGroup1.class)
	@Email(groups = ValidGroup2.class)
	private String userId;
	
	private String phoneNumber;
	
	private Integer postalNumber1;

	private Integer postalNumber2;
	
	private String address;
	
	@NotBlank(groups = ValidGroup1.class)
	@Length(min=8, max=24 , groups = ValidGroup2.class)
	private String userName;
	
	@NotBlank(groups = ValidGroup1.class)
	@Pattern(regexp="^(?=.*[A-Z])[a-zA-Z0-9_]+$", groups = ValidGroup2.class) // 少なくとも大文字が1つ含まれる
	@Length(min=8, max=24 , groups = ValidGroup2.class)
	private String password;
	
	@DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
	@Past(groups = ValidGroup2.class) // 過去の日付
	private Date birthday1;
	
	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date birthday2;
	
	@Min(value=5, groups = ValidGroup2.class)
	@Max(value=100, groups = ValidGroup2.class)
	private Integer age;
	
	private MultipartFile accountIcon1;
	
	private MultipartFile accountIcon2;
	
	private Integer gender;
	
	private Integer departmentId;
}
