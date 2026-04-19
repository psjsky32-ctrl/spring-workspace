package com.ktdsuniversity.edu.common.utils;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ktdsuniversity.edu.members.vo.MembersVO;

/**
 * Spring Security의 인증 및 권한을 편하게 체크할 수 있도록 해주는 유틸리티 클래스.
 */
public abstract class AuthUtils {

	private AuthUtils() {}
	
	// Authentication이 null이 아닌지 검사하는 기능.
	public static boolean isAuthenticated() {
		Authentication authentication = SecurityContextHolder.getContext()
															 .getAuthentication();
		return authentication != null;
	}
	
	// Authentication 토큰에서 MembersVO를 가져오는 기능.
	public static MembersVO getPrincipal() {
		//로그인 여부 확인
		if (isAuthenticated()) {
			// 인증 객체 가져오기?
			Authentication authentication = SecurityContextHolder.getContext()
					 											 .getAuthentication();
			// 인증 객체 안에 저장된 실제 사용자 정보를 MembersVO 타입으로 형변환하여 반환
			return (MembersVO) authentication.getPrincipal();
		}
		return null;
	}
	
	// Authentication 토큰에서 사용자 이메일을 가져오는 기능.
	public static String getUsername() {
		if (isAuthenticated()) {
			return getPrincipal().getEmail();
		}
		return null;
	}
	
	// Authentication 토큰에 일부 권한이 부여되어있는지 검사하는 기능.
	public static boolean hasAnyRole(String ... roles) {
		if (isAuthenticated()) {
			List<String> grantedRoles = getPrincipal().getRoles();
			//사용자의 권한 가져오기
			for (String role : roles) {
				if (grantedRoles.contains(role)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
}