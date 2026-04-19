package com.ktdsuniversity.edu.members.vo.response;

public class DuplicateResultVO {
	
	private String email;
	private boolean duplicate;
	
	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isDuplicate() {
		return this.duplicate;
	}
	public void setDuplicate(boolean duplicate) {
		this.duplicate = duplicate;
	}
	
}
