package com.ktdsuniversity.edu.members.vo.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegistVO {
	
	@NotEmpty(message="이메일 형태가 아니에영")
	@Email(message="이메일 형태가 아니에염")
	private String email;
	
	@NotEmpty(message="이름은 반드시 입력해야함")
	@Size(min = 2, max = 4, message = "이름은 2글자 이상 4글자 이하로 작성하세요")
	private String name;
	
//	@NotEmpty(message="비밀번호는 입력하셔해여")
	@Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$"
			, message= "비밀번호는 영소문자,영대문자,숫자,특수문자가 포함되어 있어야 합니다 8글자 이상 입력하세요")
	
	private String salt;
	
	
	public String getSalt() {
		return this.salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	private String password;
	
	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
