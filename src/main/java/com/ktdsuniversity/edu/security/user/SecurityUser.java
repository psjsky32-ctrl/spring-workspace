package com.ktdsuniversity.edu.security.user;

import java.util.Collection;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ktdsuniversity.edu.members.vo.MembersVO;

public class SecurityUser implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -450098395566552868L;

	private MembersVO membersVO;

	public SecurityUser(MembersVO membersVO) {
		this.membersVO = membersVO;
	}
	
	//회원 데이터에 접근하기
	public MembersVO getMembersVO() {
		return this.membersVO;
	}

	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// 사용자의 권한
		return this.membersVO.getRoles() // 회원 VO에서 권한 리스트(String 목록)를 가져옴
							 .stream() // 리스트를 순차적으로 처리하기 위해 스트림으로 변환
							 .map(role -> new SimpleGrantedAuthority("ROLE_" + role)) // 각 권한 문자열 앞에 "ROLE_"를 붙여 권한 객체로 변환
							 .toList(); //리스트 형태 만들기
	}

	@Override
	public @Nullable String getPassword() {
		// 사용자의 비밀번호
		return this.membersVO.getPassword();
	}

	@Override
	public String getUsername() {
		// 사용자의 아이디
		return this.membersVO.getEmail();
	}

	@Override
	public boolean isAccountNonLocked() {
		// 계정 잠금 여부를 확인
		return this.membersVO.getBlockYn().equals("N");
	}
}
