package com.ktdsuniversity.edu.security.authenticate.service;

import org.jspecify.annotations.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ktdsuniversity.edu.members.helpers.SHA256Util;

public class SecurityPasswordEncoder implements PasswordEncoder {

	/**
	 * 사용자가 입력한 평문 비밀번호를 암호화하여 반환.
	 * @param rawPassword 사용자가 입력한  비밀번호
	 * @return 암호화 처리된 비밀번호 문자열
	 */
	@Override
	public @Nullable String encode(@Nullable CharSequence rawPassword) {
		return null;
	}
	
	/**
	 * 사용자가 로그인을 위해 입력한 비밀번호와 
	 * DB에 저장되어 있는 암호화된 비밀번호(encoded)가 일치하는지 비교.
	 * @param rawPassword 사용자가 방금 입력한 평문 비밀번호
	 * @param encodedPassword DB에서 조회해온 이미 암호화된 비밀번호
	 * @return 두 비밀번호가 일치하면 true, 그렇지 않으면 false
	 */
	@Override
	public boolean matches(@Nullable CharSequence rawPassword, @Nullable String encodedPassword) {
		return false;
	}
	
	//사용자가 입력한 비밀번호에 암호화
	public String encode(String rawPassword, String salt) {		
		return SHA256Util.getEncrypt(rawPassword, salt);
	}
	//사용자가 입력한 비밀번호 = DB 비밀번호 비교
	public boolean matches(String rawPassword, String salt, String encodedPassword) {
		return this.encode(rawPassword, salt).equals(encodedPassword);
	}
}
