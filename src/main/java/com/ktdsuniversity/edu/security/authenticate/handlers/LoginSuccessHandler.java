package com.ktdsuniversity.edu.security.authenticate.handlers;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.ktdsuniversity.edu.common.utils.StringUtils;
import com.ktdsuniversity.edu.members.dao.MembersDao;
import com.ktdsuniversity.edu.members.vo.request.LoginVO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

private MembersDao membersDao;
	
	public LoginSuccessHandler(MembersDao membersDao) {
		this.membersDao = membersDao;
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		LoginVO loginVO = new LoginVO();
		//로그인 사용자의 IP 저장
		loginVO.setIp(request.getRemoteAddr());
		//로그인 사용자의 email 저장
		loginVO.setEmail(authentication.getName());
		//DB에 로그인 성공 업데이트
		this.membersDao.updateSuccessLogin(loginVO);
		
		//HttpServletRequest에서 파라미터를 가져오는 방법.
		String go = request.getParameter("go");
		//go에 값이 있으면 go의 값으로 없으면 "/" 여기로 이동
		response.sendRedirect( StringUtils.emptyTo(go, "/"));
	}

}
