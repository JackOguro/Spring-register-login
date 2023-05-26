package com.example.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        // パスワードの暗号化用に、BCrypt(ビー・クリプト)を使用します
        return new BCryptPasswordEncoder();
    }
	
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	
    	// URL毎の認可設定記述開始
        http.authorizeHttpRequests(auth -> auth
        		.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
        		// /user/signup, /loginはログイン無しでもアクセス可能
        		.requestMatchers("/login", "/user/signup").permitAll()
                // adminはADMINユーザーだけアクセスを許可する
                // ファイルの配置ではなくURL
                //.mvcMatchers("/admin").hasAuthority(Authority.ADMIN.name())
                // 他のURLはログインのみアクセス可能
                .anyRequest().authenticated()
            )
        .formLogin(login -> login
        		// これを設定しないとデフォルトのログインページが表示されてしまう
        		// コントローラーの@GetMapping("/login")と一致させる
                .loginPage("/login")
                .defaultSuccessUrl("/user/list")
                // login画面のinputタグのname属性と一致させる
                // デフォルトはusername, password
                .usernameParameter("userId")
                .passwordParameter("password")
                .permitAll()
            )
        .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .permitAll()
            )
        // keyには、お好きな文字列を入れてください
        .rememberMe().key("Unique and Secret");
        
        return http.build();
	}
    
//    @Bean
//    public UserDetailsService userDetailsService() {
//    	// パスワードの暗号化を設定
//    	UserBuilder users = User.builder().passwordEncoder(passwordEncoder()::encode);
//        // "user"を作成
//        UserDetails user = users.username("user")
//                // "password"をBCryptで暗号化
//        		.password("user")
//                // 権限を設定
//                .authorities("USER")
//                .build();
//        
//        UserDetails admin = users.username("admin")
//                // "password"をBCryptで暗号化
//        		.password("admin")
//                // 権限を設定
//                .authorities("ADMIN")
//                .build();
//        // メモリ内認証を使用
//        return new InMemoryUserDetailsManager(user, admin);
//    	
//    	// ユーザーデータで認証
//    	
//    }
}

