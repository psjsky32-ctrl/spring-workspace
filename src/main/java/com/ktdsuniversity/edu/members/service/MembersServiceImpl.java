package com.ktdsuniversity.edu.members.service;

import java.time.LocalDateTime;	
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktdsuniversity.edu.exceptions.handlers.HelloSpringException;
import com.ktdsuniversity.edu.members.dao.MembersDao;
import com.ktdsuniversity.edu.members.helpers.SHA256Util;
import com.ktdsuniversity.edu.members.vo.MembersVO;
import com.ktdsuniversity.edu.members.vo.request.LoginVO;
import com.ktdsuniversity.edu.members.vo.request.RegistVO;
import com.ktdsuniversity.edu.members.vo.request.UpdateVO;
import com.ktdsuniversity.edu.members.vo.response.SearchResultVO;

import jakarta.validation.Valid;

@Service
public class MembersServiceImpl implements MembersService {

	@Autowired
	private MembersDao membersDao;
	
	@Transactional
	@Override
	public boolean createNewMember(RegistVO registVO) {
		
		MembersVO membersVO = this.membersDao.selectMemberByEmail(registVO.getEmail());
		if(membersVO != null) {
			throw new HelloSpringException("이미 사용중인 이메일입니다.", "members/regist", registVO);
		}
		
		//암호화를 위한 비밀키 생성
		String newSalt = SHA256Util.generateSalt();
		String userPassword = registVO.getPassword();
		//사용자가 입력한 비밀번호를 newSalt를 이용해 암호화
		//비밀번호 newSalt의 값이 일치하면, 항상 같은 값의 암호화 결과가 생성된다.
		userPassword = SHA256Util.getEncrypt(userPassword, newSalt);
		registVO.setSalt(newSalt);
		registVO.setPassword(userPassword);
		
		int insertCount = this.membersDao.insertNewMember(registVO);
		return insertCount == 1;
	}

	@Override
	public MembersVO findMemberByEmail(String email) {
		MembersVO searchResult = this.membersDao.selectMemberByEmail(email);
		return searchResult;
	}

	@Transactional
	@Override
	public boolean updateMemberByEmail(UpdateVO updateVO) {
		int updateCount = this.membersDao.updateMemberByEmail(updateVO);
		return updateCount == 1;
	}

	@Transactional
	@Override
	public boolean deleteMemberByEmail(String email) {
		int deleteCount = this.membersDao.deleteMemberByEmail(email);
		return deleteCount == 1;
	}

	@Override
	public SearchResultVO findAllMembers() {
		SearchResultVO result = new SearchResultVO();
		
		int Count = this.membersDao.selectMembersCount();
		result.setCount(Count);
		
		if(Count == 0) {
			return result;
		}
		
		List<MembersVO> list = this.membersDao.selectMembersList();
		result.setResult(list);
		
		return result;
	}

	@Transactional(noRollbackFor = HelloSpringException.class)
	@Override
	public MembersVO findMemberByEmailAndPassword(LoginVO loginVO) {
		// 1.Email을 이용해 회원 정보 조회하기(selectMemberByEmail)
		MembersVO member = this.membersDao.selectMemberByEmail(loginVO.getEmail());
		// 2.조회된 결과가 없다면 "이메일 또는 비밀번호가 잘못되었습니다." 예외 던지기
		if(member == null) {
			// IllegalArgumentsException
			throw new HelloSpringException("이메일 또는 비밀번호가 잘못되었습니다.", "members/login", loginVO );
		}
		
		if(member.getBlockYn().equals("Y")) {
			
			//로그인 Block 된 시간으로부터 120분이 지나면 다시 로그인 가능한 상태로 변경한다
			String latestLoginFailDate = member.getLatestLoginFailDate();
			
			DateTimeFormatter dateTimePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime lastestBlockDateTime = LocalDateTime.parse(latestLoginFailDate, dateTimePattern);
			
			if(lastestBlockDateTime.isAfter(LocalDateTime.now().minusMinutes(120))) {
				throw new HelloSpringException("2시간 뒤에 해보세요.", "members/login", loginVO );
			}
			// 이 경우엔 예외를 던지기 X
		}
		// 3.조회된 결과가 있다면 사용자가 전송한 비밀번호와 조회된 회원의 salt를 이용해 SHA 암호화 하기
		String inputPassword = loginVO.getPassword(); 
		String storeSalt = member.getSalt();
		//소금을 추가하는데 loginVO.getPassword() 
		//그 소금은 member.getPassword()에 추가한 소금을  inputpasword에 추가를한다 샤갈
		String encrytedPassword = SHA256Util.getEncrypt(inputPassword, storeSalt);
		// 4.3에서 암호화 한 비밀번호와 1에서 조회한 비밀번호가 일치하는지 확인하기.
		if(!encrytedPassword.equals(member.getPassword())) {
			
			//해당 이메일의 로그인 실패 횟수를 1증가시키고
			//최근 로그인 실패 날짜를 현재 날짜와 시간으로 변경한다.
			this.membersDao.updateIncreaseLoginFailCount(loginVO.getEmail());
			
			//최근 로그인 실패 횟수가 5 이상이라면 block-yn을 Y로 변경한다.
			this.membersDao.updateBlock(loginVO.getEmail());
			
			throw new HelloSpringException("이메일 또는 비밀번호가 잘못되었습니다.", "members/login", loginVO );
		}
		//로그인 성공처리
		//1. login_fail_count를 0으로 초기화
		//2. latest_login_ip를 현재 아이피로 변경.
		//3. login_date를 현재 시간으로 변경
		//4. block_yn을 'N'으로 변경
		this.membersDao.updateSuccessLogin(loginVO);
		// 6.비밀번호가 일치하면 1에서 조회한 결과를 반환
		return member;
	}







}