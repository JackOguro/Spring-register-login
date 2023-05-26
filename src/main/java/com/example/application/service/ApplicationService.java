package com.example.application.service;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface ApplicationService {

	/* 性別のMapを生成する */
	public Map<String, Integer> getGenderMap(Locale locale);
		
	/* アップロード実行処理1 */
	public byte[] uploadFile1(MultipartFile multipartFile);
	
	/* アップロード実行処理2 */
	public String uploadFile2(MultipartFile multipartFile);
	
	/* デフォルト画像をbyte[]型に変更 */
	public byte[] changedByte() throws IOException;
}
