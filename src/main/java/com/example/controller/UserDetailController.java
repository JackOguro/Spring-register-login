package com.example.controller;

import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.annotation.SessionScope;

import com.example.application.service.ApplicationService;
import com.example.form.UserDetailForm;
import com.example.model.LoginUser;
import com.example.model.MUser;
import com.example.model.TPurchase;
import com.example.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/user")
@SessionScope
@Slf4j
public class UserDetailController {

	@Autowired
	private UserService userservice;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private LoginUser user;
	
	
	/* ユーザーの詳細情報を表示 */
	@GetMapping("/detail/{userId:.+}")
	public String getUser(UserDetailForm form, Model model, @PathVariable("userId") String UserId) throws Exception {
		
		// セッションスコープ内の情報を取得
		model.addAttribute("user", user);
		
		// ユーザーを1件取得
		MUser user = userservice.getOneMUser(UserId);
		
		// ユーザーの購入履歴を取得
		List<TPurchase> purchase = userservice.getTPurchase(UserId);
		
		// accountIconがnullの場合
		if(user.getAccountIcon1() == null) {
			
			// アカウントアイコンが設定されていない場合、デフォルト画像をbyte[]型に変換し表示する
			byte[] bytes = applicationService.changedByte();
			user.setAccountIcon1(bytes);
		}
		
		//log.info(user.toString());
		log.info(purchase.toString());
		
		StringBuffer stringBuffer  = new StringBuffer();
		
		// base64にエンコードしたものを文字列に変更
		String base64 = new String(Base64.encodeBase64(user.getAccountIcon1(), true));
		
		// 拡張子をjpegと指定
		// <img ht:src="">で指定できる形にする
		stringBuffer.append("data:image/jpeg;base64,");
		stringBuffer.append(base64);
		
		model.addAttribute("base64AccountIcon",stringBuffer.toString());
		
		// MUserをuseDetailFormに変換
		form = modelMapper.map(user, UserDetailForm.class);
		//form.setPurchaseList(user.getPurchaseList());
		form.setPurchaseList(purchase);
		
		log.info(form.toString());
		
		// Modelに登録
		model.addAttribute("userDetailForm", form);
		
		// ユーザー詳細情報を表示
		return "user/detail";
	}
	
	/* ユーザー更新情報 */
	@PostMapping(value="/detail", params="update")
	public String updateUser(UserDetailForm form, Model model) {
		
		// formをMUserクラスに変換
//		MUser user = modelMapper.map(form, MUser.class);
		
		try {
			// ユーザー情報を更新
			userservice.updateMUserOne(form.getUserId(), form.getPassword(), form.getUserName());
//			userservice.updateMUserOne(user);
		
		} catch(Exception e) {
			log.error("ユーザー更新でエラー", e);
		}

		
		// ユーザー一覧画面にリダイレクト
		return "redirect:/user/list";
	}
	
	/* ユーザー削除処理 */
	@PostMapping(value="/detail", params="delete")
	public String deleteUser(UserDetailForm form, Model model) {
		// ユーザー情報を削除
		userservice.deleteMUserOne(form.getUserId());
		
		// ユーザー一覧画面にリダイレクト
		return "redirect:/user/list";
	}
}
