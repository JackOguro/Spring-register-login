package com.example.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.model.MDepartment;
import com.example.model.MUser;
import com.example.model.TPurchase;

@Mapper
public interface UserMapper {
	
	/* ユーザー登録 */
	public int insertOne(MUser user);
	
	/* ユーザー表示(複数件の場合はリスト型で返す) */
	public List<MUser> findAllMUser(MUser user);
	
	/* 検索条件で一致するユーザーを取得 */
	public List<MUser> searchMUser(MUser user);
	
	/* 検索条件で一致するユーザーの購入履歴を取得 */
	public List<TPurchase> findTPurchase(String user);
	
	/* ユーザー取得(1件) */
	public MUser findOneMUser(String UserId);
	
	/* ユーザー更新(1件) */
	public void updateOneMUser(@Param("userId") String userId,
			@Param("password") String password,
			@Param("userName") String userName);
//	public void updateOneMUser(MUser user);
//	public void updateOneMUser(@Param("user") MUser user);
	
	/* ユーザー削除(1件) */
	public int deleteOneMUser(@Param("userId") String userId);
//	public int deleteOneMUser(MUser user);
//	public int deleteOneMUser(@Param("user") MUser user);
	
	/* ログインユーザーの取得 */
	public MUser findLoginUser(String userId);
	
	/* 店舗情報を取得 */
	public List<MDepartment> findAllMDepartment();
}
