package com.example.ex.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration			// 이 파일이 스프링 환경 설정 파일이라는것을 의미
@EnableWebSecurity	// 모든 요청 URL 이 스프링 시큐리티의 제어를 받도록 만든다
@EnableMethodSecurity(prePostEnabled = true)// @PreAuthorize("isAuthenticate()") <-  이거쓰기위해서 씀
public class SecurityConfig {
	
	@Bean // 빈객체주입
	// 필터 체인을 정의하는 메서드
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http
				// 요청을 허용할지 결정하는 설정
				.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
						.requestMatchers(new AntPathRequestMatcher("/**")).permitAll())		//모든 요청을 허용한다는 의미
				.csrf((csrf)->csrf
							.ignoringRequestMatchers(new AntPathRequestMatcher
									("/h2-console/**")))												// 특정요청에대한 보호를 비활성화
				.headers((header) -> header
						.addHeaderWriter(new XFrameOptionsHeaderWriter(
								XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))// X-Frame-Options 헤더를 SAMEORIGIN으로 설정하여 동일 출처의 프레임만 허용.
				.formLogin((formLogin) -> formLogin		// 사용자 정의
								.loginPage("/user/signin")		// 로그인페이지 설정과
								.usernameParameter("userName")
								.passwordParameter("password") 		//default = password, username
								.defaultSuccessUrl("/board/list",true))	// 리다이렉트 URL 설정	
				.logout((logout)->logout							//사용자 정의
							.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")) // 로그아웃 URL 과
							.logoutSuccessUrl("/board/list")		// 성공시 리다이렉트 URL 설정
							.invalidateHttpSession(true))				// 세션 삭제
				;
		return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();		// 비밀번호 암호화하는 인코더
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
		return authenticationConfiguration.getAuthenticationManager();			// 사용자 인증,권한 관리하는 객체
		
	}
}
