package com.example.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LogAspect {

	/**
	 * サービスの実行前にログを出力
	 * 対象:[UserService]をクラス名に含んでいる
	 */
//	 @Before("execution(* *..*.*UserService.*(..))")
//	 public void startLog(JoinPoint jp) {
//		 log.info("メソッド開始：" + jp.getSignature());
//	 }
//	 
//	 /**
//	  * サービスの実行後にログを出力
//	  * 対象:[UserService]をクラス名に含んでいる
//	  */
//	 @After("execution(* *..*.*UserService.*(..))")
//	 public void endLog(JoinPoint jp) {
//		 log.info("メソッドの終了：" + jp.getSignature());
//	 }
	 
	 /** コントローラーの実行前後にログを出力する */
	 // @Around("bean(*Controller)")
	 // @Around("@annotation(org.springframework.web.bind.annotation.GetMapping)")
	 @Around("@within(org.springframework.stereotype.Controller)")
	 public Object startLog(ProceedingJoinPoint jp) throws Throwable {
		 
		 // 開始ログの出力
		 log.info("メソッドの開始：" + jp.getSignature());
		 
		 try {
			 //メソッドの実行
			 Object result = jp.proceed();
			 
			 // メソッドの実行
			 log.info("メソッドの終了：" + jp.getSignature());
			 
			 // 実行結果を呼び出し元に返却
			 return result;
			 
		 } catch(Exception e) {
			 // エラーログ出力
			 log.error("メソッド異常終了：" + jp.getSignature());

			 
			 // エラーの再スロー
			 throw e;
		 }
	 }
}
