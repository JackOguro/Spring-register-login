package com.example.controller;

import java.util.Locale;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.application.service.ApplicationService;
import com.example.form.GroupOrder;
import com.example.form.SignupForm;
import com.example.model.MUser;
import com.example.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/user")
@Slf4j
public class SignupController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private ModelMapper modelMapper;

	/* ユーザー登録画面を表示 */
	@GetMapping("/signup")
	public String getSignup(Model model, Locale locale, @ModelAttribute SignupForm form) {
		// 性別を取得
		Map<String, Integer> genderMap = applicationService.getGenderMap(locale);
		model.addAttribute("genderMap", genderMap);
		
		// 店舗を取得
		Map<String, Integer> mDepartmentMap = userService.getDepartmentMap();
		model.addAttribute("mDepartmentMap", mDepartmentMap);
		
		// ユーザー登録画面に遷移
		return "user/signup";
	}
	
	/* ユーザー登録処理 */
	@PostMapping("/signup")
	public String postSignup(Model model, Locale locale, 
			@Validated(GroupOrder.class) @ModelAttribute SignupForm form,
			BindingResult bindingResult) {
		
		log.info(form.toString());
		
		// 入力チェック
		if(bindingResult.hasErrors()) {
			// エラーが発生したので登録画面に戻る
			return getSignup(model, locale, form);
		}
		
		// フォームに渡されたアップロードファイルを取得
		MultipartFile multipartFile1 = form.getAccountIcon1(); 
		
		MultipartFile multipartFile2 = form.getAccountIcon2();
		
		// formをMUserクラスに変換
		MUser user = modelMapper.map(form, MUser.class);
		
		// アップロード実行処理メソッドの呼び出し(byte[]型)
		user.setAccountIcon1(applicationService.uploadFile1(multipartFile1));
		
		// アップロード実行処理メソッドの呼び出し(String型)
		user.setAccountIcon2(applicationService.uploadFile2(multipartFile2));
		
		log.info(user.toString());
		
		// ユーザ登録
		userService.signUp(user);
		
		// ログイン画面にリダイレクト
		// ?registerと付けることでパラメータを渡すことができる
		return "redirect:/login?register";
	}
	
	/* データベース関連の例外処理 */
	@ExceptionHandler(DataAccessException.class)
	public String dataAccessExceptionHandler(DataAccessException e, Model model) {
		
		// 空文字をセット
		model.addAttribute("error", "");
		
		// メッセージをModelに登録
		model.addAttribute("message", "SignupControllerで例外が発生しました");
		
		// HTTPのエラーコード(500)をModelに登録
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
		
		return "error";
	}
}
