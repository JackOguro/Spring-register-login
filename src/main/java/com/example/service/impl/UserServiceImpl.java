package com.example.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.MDepartment;
import com.example.model.MUser;
import com.example.model.TPurchase;
import com.example.repository.UserMapper;
import com.example.service.UserService;
import com.example.util.Authority;

@Service
public class UserServiceImpl implements UserService {
	
	/* レポジトリー(UserMapper.java)のDIを注入 */
	@Autowired
	private UserMapper mapper;
	
	@Autowired
	private PasswordEncoder encoder;
		
	/* データの挿入 */
	@Override
	public void signUp(MUser user) {
		
		user.setAuthority(Authority.GENERAL);
		
		// パスワードの暗号化
		String rawPassword = user.getPassword();
		user.setPassword(encoder.encode(rawPassword));
		
		mapper.insertOne(user);
	}

	/* ユーザー情報取得 */
	@Override
	public List<MUser> getAllMUser(MUser user) {
		return mapper.findAllMUser(user);
	}
	
	/* 検索条件で一致するユーザーを取得 */
	@Override
	public List<MUser> getSearchMUser(MUser user) {
		return mapper.searchMUser(user);
	}

	/* ユーザー情報取得(1件) */
	@Override
	public MUser getOneMUser(String UserId) {
		return mapper.findOneMUser(UserId);
	}
	
	/* 検索条件に一致するユーザーの購入履歴を取得 */
	@Override
	public List<TPurchase> getTPurchase(String user) {
		return mapper.findTPurchase(user);
	}

	/* ユーザー更新(1件) */
	@Transactional
	@Override
	public void updateMUserOne(String userId, String password, String userName) {
		
		// パスワードの暗号化
		String encyptPassword = encoder.encode(password);
		
		mapper.updateOneMUser(userId, encyptPassword, userName);

		// わざと例外を発生させる
		// int i = 1/0;
//	public void updateMUserOne(MUser muser) {
//		mapper.updateOneMUser(muser);
	}

	/* ユーザー削除(1件) */
	@Override
	public void deleteMUserOne(String userId) {
		int count = mapper.deleteOneMUser(userId);
	}
	
	/* ログインユーザー情報取得 */
	@Override
	public MUser getLoginUser(String userId) {
		return mapper.findLoginUser(userId);
	}
	
	/* 店舗のMapを生成する */
	@Override
	public Map<String, Integer> getDepartmentMap() {
		List<MDepartment> mDepartment = mapper.findAllMDepartment();
		Map<String, Integer> mDepartmentMap = new LinkedHashMap<>();
		
		for(MDepartment row : mDepartment) {
			String departmentName = row.getDepartmentName();
			Integer departementId = row.getDepartmentId();
			mDepartmentMap.put(departmentName, departementId);
		}
		return mDepartmentMap;
	}
}
