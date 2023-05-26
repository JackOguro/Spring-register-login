package com.example.application.service.Impl;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.application.service.ApplicationService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ApplicationServiceImpl implements ApplicationService {
	
	/* messages.propertiesのDIを注入 */
	@Autowired
	private MessageSource messagesource;
	
	/* 性別のMapを生成する */
	@Override
	public Map<String, Integer> getGenderMap(Locale locale) {
		Map<String, Integer> genderMap = new LinkedHashMap<>();
		String male = messagesource.getMessage("male", null, locale);
		String female = messagesource.getMessage("female", null, locale);
		genderMap.put(male, 1);
		genderMap.put(female, 2);
		return genderMap;
	}
		
	/* アップロードの実行処理1 */
	@Override
	public byte[] uploadFile1(MultipartFile multipartFile) {		
		try {
			// アップロードファイルをバイト値に変換
			byte[] bytes = multipartFile.getBytes();
			
			return bytes;
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/* アップロードの実行処理2 */
	@Override
	public String uploadFile2(MultipartFile multipartFile) {
		
		// ファイル名取得
        String fileName = multipartFile.getOriginalFilename();

        //格納先のフルパス ※事前に格納先フォルダ「UploadTest」をCドライブ直下に作成しておく
        Path filePath = Paths.get("C:/pleiades/2022-06/workspace/SpringOracleSample/src/main/resources/static/img/" + fileName);
        
        // データベースに格納するパスはstaticからの相対パス
        String DbPath = ("/img/" + fileName);
        
        log.info(filePath.toString());
        
		try {
            //アップロードファイルをバイト値に変換
			byte[] bytes = multipartFile.getBytes();

            //バイト値を書き込む為のファイルを作成して指定したパスに格納
            OutputStream stream = Files.newOutputStream(filePath);
			
            //ファイルに書き込み
            stream.write(bytes);
            
			// ファイルを読み込む
			//BufferedImage bufferedImage = ImageIO.read(new File(multipartFile));
			
			// jpg形式で保存する
			//ImageIO.write(bufferedImage, "jpg", new File(filePath.toString()));
			
			// ファイルパスを戻す
			return DbPath;
						
		} catch (IOException e) {
			e.printStackTrace();
			
			// バイト型の変換に失敗した場合nullを返す
			return null;
		}
		
		
	}
	
	@Override
	public byte[] changedByte() throws IOException {
		//Resource resource = resourceLoader.getResource("classpath:" + "/img/default.png");
		// パスの設定
		String filePath = "C:/pleiades/2022-06/workspace/SpringOracleSample/src/main/resources/static/img/default.png";
		File file = new File(filePath);
		
		// バイト型に変更
		byte[] bytes = Files.readAllBytes(file.toPath());
				
		return bytes;
	}
}
