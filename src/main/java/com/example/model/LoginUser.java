package com.example.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Data;

@Component
@SessionScope
@Data
public class LoginUser {
	
	private String loginUserAccountIcon;
	private String loginUserName;
}
