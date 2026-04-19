package com.ktdsuniversity.edu.board.vo.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/**
 * 게시글 등록을 위해
 * 브라우저에서 컨트롤러(엔드포인트)로 전송되는
 * 파라미터를 받아오기 위한 클래스
 * 
 * Spring이 파라미터를 WriteVO의 멤버변수로 할당할 때 
 * setter를 이용. 
 */
public class WriteVO {
	
	private String id;
	
	@NotEmpty(message = "제목은 반드시 입력해주세요")
	@Size(min = 2, message="제목은 3글자 이상 적어주셍")
	private String subject;
	
	private String email;
	
	private String content;
	//컬렉션을 전달하기때문에 list
	//업로드한 파일은 항상 MultipartFIle로 가지고온다 스프링전용?
	private List<MultipartFile> attachFile;
	
	private String fileGroupId;
	

	public String getFileGroupId() {
		return this.fileGroupId;
	}
	public void setFileGroupId(String fileGroupId) {
		this.fileGroupId = fileGroupId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContent() {
		return this.content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<MultipartFile> getAttachFile() {
		return this.attachFile;
	}
	public void setAttachFile(List<MultipartFile> attachFile) {
		this.attachFile = attachFile;
	}
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	
}
