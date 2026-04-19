package com.ktdsuniversity.edu.security.authenticate.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ktdsuniversity.edu.members.dao.MembersDao;
import com.ktdsuniversity.edu.members.vo.MembersVO;
import com.ktdsuniversity.edu.security.user.SecurityUser;

public class SecurityUserDetailsService implements UserDetailsService {

	// DB 접근을 위한 DAO
	private MembersDao membersDao;
	
	public SecurityUserDetailsService(MembersDao membersDao) {
		this.membersDao = membersDao;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 로그인 시 입력한 아이디 )를 이용해 사용자 정보를 가져 오기
		
		// 이메일로 사용자 정보 가져오기
		MembersVO loadUser = this.membersDao.selectMemberByEmail(username);
		// 사용자 정보가 없다면 예외 던지기
		if(loadUser == null) {
			throw new UsernameNotFoundException("아이디 또는 비밀번호가 이상해요");
		}
		
		// 예외가 발생되지 않았다면 사용자 권한 조회
		List<String> UserRole = this.membersDao.selectMemberRolesByEmail(username);
		// 조회 결과 넣기
		loadUser.setRoles(UserRole);
		
		return new SecurityUser(loadUser);
	}

}
