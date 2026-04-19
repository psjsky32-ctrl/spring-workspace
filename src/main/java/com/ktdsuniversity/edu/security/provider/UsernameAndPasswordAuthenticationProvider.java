package com.ktdsuniversity.edu.security.provider;

import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ktdsuniversity.edu.members.vo.MembersVO;
import com.ktdsuniversity.edu.security.authenticate.service.SecurityPasswordEncoder;
import com.ktdsuniversity.edu.security.user.SecurityUser;

/**
 * 아이디와 비밀번호를 받아 실제 인증 로직을 수행하는 클래스
 */
public class UsernameAndPasswordAuthenticationProvider implements AuthenticationProvider {

	// DB에서 사용자 정보를 가져오는 서비스
	private UserDetailsService userDetailsService;
	
	// 비밀번호를 비교/검증하는 도구
	private PasswordEncoder passwordEncoder;
	
	/**
	 * 생성자를 통해 필요한 서비스들을 주입받음
	 */
	public UsernameAndPasswordAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder){
		this.passwordEncoder = passwordEncoder;
		this.userDetailsService = userDetailsService;
	}

	/**
	 * 실제 인증 과정을 처리하는 핵심 메소드
	 */
	@Override
	public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		// 사용자가 입력한 아이디
		String email = authentication.getName();
		
		// 아이디를 이용해 DB에서 사용자 정보를 조회
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
		
		// 사용자의 계정이 잠겨있는 상태인지 확인
		if(!userDetails.isAccountNonLocked()) {
			throw new LockedException("잠김");
		}
		
		// 사용자가 입력한 평문 비밀번호를 가지고온다
		String rawPassword = authentication.getCredentials().toString();
		
		// 조회된 정보를 SecurityUser로 형변환하여 내부의 MembersVO를 꺼내기
		MembersVO membersVO = ((SecurityUser) userDetails).getMembersVO();
		
		// PasswordEncoder를  SecurityPasswordEncoder로 형변환 salt 사용하기위해서?
		SecurityPasswordEncoder passwordComparator = (SecurityPasswordEncoder) this.passwordEncoder;
		
		// 비번, DB의 Salt, DB의 암호화된 비번을 비교
		boolean isMatch = passwordComparator.matches(rawPassword, membersVO.getSalt(), userDetails.getPassword());
		
		// 비밀번호가 일치하지 않으면 예외 처리
		if(!isMatch) {
			throw new BadCredentialsException("비밀번호가 이상해요");
		}
		
		// 인증 성공 시, 회원 정보와 권한을 담은 인증 토큰을 생성
		return new UsernamePasswordAuthenticationToken(membersVO, userDetails.getPassword(), userDetails.getAuthorities());
	}

	/**
	 * 이 Provider가 어떤 종류의 인증 객체를 지원하는지 설정
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		// UsernamePasswordAuthenticationToken 형식의 인증 요청일 때만 이 Provider를 사용
		return authentication.equals( UsernamePasswordAuthenticationToken.class);
	}
}