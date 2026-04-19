package com.ktdsuniversity.edu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ktdsuniversity.edu.members.dao.MembersDao;
import com.ktdsuniversity.edu.security.authenticate.handlers.LoginFailureHandler;
import com.ktdsuniversity.edu.security.authenticate.handlers.LoginSuccessHandler;
import com.ktdsuniversity.edu.security.authenticate.service.SecurityPasswordEncoder;
import com.ktdsuniversity.edu.security.authenticate.service.SecurityUserDetailsService;
import com.ktdsuniversity.edu.security.provider.UsernameAndPasswordAuthenticationProvider;

//application.yml에서 작성할 수 없는 설정들을 적용하기 위한 Annotation
//@Component 의 자식 Annotation
@Configuration
//spring-boot-starter-validation 동작 활성화 시키기
//@EnableWebMvc가 추가되면 application.yml의 mvc 관련 설정들이 모두 무시된다.
//1. spring.mvc.view.prefix, spring.mvc.view.suffix
//2. src/main/resources/static 경로 사용 불가능.
@EnableWebMvc
//생략 가능
//Spring Security 라이브러리를 활성화 시킨다.
//Spring Security의 필터목록을 확인하기 위해서 작성한다.
@EnableWebSecurity(debug = true)
//컨트롤러 혹은 서비스코드에서 권한 검사를 수행하기 위한 애노테이션 추가.
@EnableMethodSecurity
public class HelloSpringConfiguration implements
		// WebMvc 설정을 위한 Configuration
		// @EnableWebMvc Annotation 에서 적용하는 기본 설정들을 변경하기 위함.
		WebMvcConfigurer {

	@Autowired
	private MembersDao membersDao;
	
	// 비밀번호를 비교하는 도구를 등록한다.
	// SecurityPasswordEncoder의 Bean을 생성한다.
	@Bean // 메소드가 실행되어서 반환되는 객체를 Bean Container에 적재한다.
	PasswordEncoder createPasswordEncoder() {
		return new SecurityPasswordEncoder();
	}

	// DB에서 사용자 정보를 찾는 도구
	// SecurityUserDetailsService의 Bean을 생성한다.
	// @Bean으로 생성하는 객체(Bean)들은 필요한 의존 객체를 생성자로 주입해 주어야 한다.
	@Bean
	UserDetailsService createUserDetailsService() {
		return new SecurityUserDetailsService(this.membersDao);
	}

	// 아이디 비번 일치하는지 검사하는 도구
	// UsernameAndPasswordAuthenticationProvider의 Bean을 생성한다.
	@Bean
	AuthenticationProvider createAuthenticationProvider() {
		UserDetailsService userDetailsService = this.createUserDetailsService();
		PasswordEncoder passwordEncoder = this.createPasswordEncoder();

		return new UsernameAndPasswordAuthenticationProvider(userDetailsService, passwordEncoder);
	}
	//로그인 성공시 동작하는 도구
	@Bean
	AuthenticationSuccessHandler createLoginSuccessHandler() {
		return new LoginSuccessHandler(this.membersDao);
	}
	
	//실패시 동작하는 도구
	@Bean
	AuthenticationFailureHandler createLoginFailureHandler() {
		return new LoginFailureHandler(this.membersDao);
	}
	
	
	// Spring Login Filter(BasicAuthenticationFilter) 등록.
	// Spring Security의 기본 로그인 절차를 수정하는 작업.
	@Bean
	SecurityFilterChain configureFilterChain(HttpSecurity httpSecurity) {
		//사용자가 로그인된 상태를 이용해, 공격자가 사용자 몰래 위조된 요청을 서버에 보내는 공격.
		// CSRF 수정, 댓글 등록 불가. (Invalid CSRF token found for  ...)
		// CSRF를 체크하는 SecurityFilter(CsrfFilter)를 무효화.
		//<sec:csrfInput/> 아이디 값과 함께 _csrf라는 이름의 토큰 값이 자동으로 넣어진다
		//
//		httpSecurity.csrf(csrf -> csrf.disable());
		
		// UsernamePasswordAuthenticationFilter 수정.
		httpSecurity.formLogin(formLogin -> 
					// Login URL 지정.
					formLogin.loginPage("/login")
					// Login 인증 처리 URL 지정 
					// (UsernameAndPasswordAuthenticationProvider가 실행될 Endpoint)
							 .loginProcessingUrl("/login-provider")
					// 로그인에 필요한 아이디 파라미터 이름을 "username"에서 "email"로 변경한다.
							 .usernameParameter("email")
					// 로그인에 성공하면 뭐할까?
					// this.membersDao.updateSuccessLogin(loginVO); 실행해야 한다.
							 .successHandler(this.createLoginSuccessHandler())
					// 로그인에 실패하면 뭐할까?
					// this.membersDao.updateIncreaseLoginFailCount(loginVO.getEmail());
					// this.membersDao.updateBlock(loginVO.getEmail());
							 .failureHandler(this.createLoginFailureHandler())
		);
		
		return httpSecurity.build();
	}

	// configureViewResolvers 설정
	// spring.mvc.view.prefix, spring.mvc.view.suffix 재설정
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/views/", ".jsp");
	}

	// addResourceHandlers
	// src/main/resources/static 경로의 endpoint 재설정
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// /static/css/ 폴더에 있는 파일들에 대한 Endpoint 설정.
		registry.addResourceHandler("/css/**") // /static/css/ 의 엔드포인트
				.addResourceLocations("classpath:/static/css/"); // /static/css/ 의 물리적인 위치

		// /static/image/ 폴더에 있는 파일들에 대한 Endpoint 설정.
		registry.addResourceHandler("/image/**") // /static/image/ 의 엔드포인트
				.addResourceLocations("classpath:/static/image/"); // /static/image/ 의 물리적인 위치

		// /static/js/ 폴더에 있는 파일들에 대한 Endpoint 설정.
		registry.addResourceHandler("/js/**") // /static/js/ 의 엔드포인트
				.addResourceLocations("classpath:/static/js/"); // /static/js/ 의 물리적인 위치
	}
}