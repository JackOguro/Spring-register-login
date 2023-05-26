package com.example.service;

import java.util.List;
import java.util.Map;

import com.example.model.MUser;
import com.example.model.TPurchase;

public interface UserService {
		
	/* ユーザー登録 */
	public void signUp(MUser muser);
	
	/* ユーザー取得 */
	public List<MUser> getAllMUser(MUser user);
	
	/* 検索条件に一致するユーザーの取得 */
	public List<MUser> getSearchMUser(MUser user);
	
	/* 検索条件に一致するユーザーの購入履歴を取得 */
	public List<TPurchase> getTPurchase(String user);
	
	/* ユーザー取得(1件) */
	public MUser getOneMUser(String UserId);
		
	/* ユーザー更新(1件) */
	public void updateMUserOne(String userId, String password, String userName);
//	public void updateMUserOne(MUser muser);
	
	/* ユーザー削除(1件) */
	public void deleteMUserOne(String userId);
//	public void deleteMUserOne(MUser muser);	
	
	/* ログインユーザー情報取得 */
	public MUser getLoginUser(String userId);
	
	/* 店舗のMapを生成する */
	public Map<String, Integer> getDepartmentMap();
}

