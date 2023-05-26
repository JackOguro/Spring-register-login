package com.example.controller;

import java.security.Principal;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.form.UserListForm;
import com.example.model.LoginUser;
import com.example.model.MUser;
import com.example.service.UserService;

@Controller
@RequestMapping("/user")
public class UserListController {
	
	@Autowired
	private UserService userservice;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private LoginUser user;
	
	/* ユーザー一覧画面を表示 */
	@GetMapping("/list")
	public String getUserList(@ModelAttribute UserListForm form, Model model, Principal principal) {
		
		// ログインユーザーの名前を取得
		String loginUserName = principal.getName();
		
		// ログインユーザーの情報を取得
		MUser loginUser = userservice.getLoginUser(loginUserName);
		
		// セッションスコープにアカウント画像のパスをセット
		user.setLoginUserAccountIcon(loginUser.getAccountIcon2());
		
		// セッションスコープにユーザー名のパスをセット
		user.setLoginUserName(loginUser.getUserName());
		
		model.addAttribute("user", user);
		
		// formをMUserクラスに変換
		MUser user = modelMapper.map(form, MUser.class);
		
		// ユーザー検索
		List<MUser> userList = userservice.getAllMUser(user);
				
		// Modelに登録
		model.addAttribute("userList", userList);
		
		return "/user/list";
	}
	
	/* ユーザー検索処理 */
	@PostMapping(value="/list", params="search")
	public String postUserList(@ModelAttribute UserListForm form, Model model) {
				
		// formをMUserクラスに変換
		MUser user = modelMapper.map(form, MUser.class);
		
		// ユーザー検索
		List<MUser> userList = userservice.getSearchMUser(user);
		
		if (userList.isEmpty() == true) {
			// 検索結果がない場合のメッセージを設定
			model.addAttribute("NoList", "検索結果がありません");
		}
		
		// Modelに登録
		model.addAttribute("userList", userList);
		
		// ユーザー一覧画面を表示
		return "user/list";
	}
	
	/* クリア処理 */
	@PostMapping(value="/list", params="clear")
	public String clearUserList() {
		
		// 一覧表示画面に戻る
		return "redirect:/user/list";
	}
}
