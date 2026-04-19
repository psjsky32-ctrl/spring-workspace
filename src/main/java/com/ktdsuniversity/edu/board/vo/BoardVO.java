package com.ktdsuniversity.edu.board.vo;

import java.util.List;

import com.ktdsuniversity.edu.files.vo.FilesVO;
import com.ktdsuniversity.edu.members.vo.MembersVO;

public class BoardVO {
	
	private String id;
	private String subject;
	private String content;
	private String email;
	private int viewCnt;
	private String crtDt;
	private String mdfyDt;
	private String fileName;
	private String originFileName;
	private String fileGroupId;
	
	private MembersVO membersVO; // 1:1?
	
	private List<FilesVO> files;
	
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSubject() {
		return this.subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return this.content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getViewCnt() {
		return this.viewCnt;
	}
	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}
	public String getCrtDt() {
		return this.crtDt;
	}
	public void setCrtDt(String crtDt) {
		this.crtDt = crtDt;
	}
	public String getMdfyDt() {
		return this.mdfyDt;
	}
	public void setMdfyDt(String mdfyDt) {
		this.mdfyDt = mdfyDt;
	}
	public String getFileName() {
		return this.fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getOriginFileName() {
		return this.originFileName;
	}
	public void setOriginFileName(String originFileName) {
		this.originFileName = originFileName;
	}
	public List<FilesVO> getFiles() {
		return this.files;
	}
	public void setFiles(List<FilesVO> files) {
		this.files = files;
	}
	public String getFileGroupId() {
		return this.fileGroupId;
	}
	public void setFileGroupId(String fileGroupId) {
		this.fileGroupId = fileGroupId;
	}
	public MembersVO getMembersVO() {
		return this.membersVO;
	}
	public void setMembersVO(MembersVO membersVO) {
		this.membersVO = membersVO;
	}
	
	
	
}
