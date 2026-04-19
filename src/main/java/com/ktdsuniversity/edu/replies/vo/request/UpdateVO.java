package com.ktdsuniversity.edu.replies.vo.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;

public class UpdateVO {
	
	private String replyId;
	@NotBlank(message = " 내용을 입력해주세요")
	private String content;
	private List<Integer> delFileNum;
	private List<MultipartFile> newAttachFile;
	
	private String fileGroupId;
	
	
	
	public String getFileGroupId() {
		return this.fileGroupId;
	}
	public void setFileGroupId(String fileGroupId) {
		this.fileGroupId = fileGroupId;
	}
	public String getContent() {
		return this.content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<Integer> getDelFileNum() {
		return this.delFileNum;
	}
	public void setDelFileNum(List<Integer> delFileNum) {
		this.delFileNum = delFileNum;
	}
	public List<MultipartFile> getNewAttachFile() {
		return this.newAttachFile;
	}
	public void setNewAttachFile(List<MultipartFile> newAttachFile) {
		this.newAttachFile = newAttachFile;
	}
	public String getReplyId() {
		return this.replyId;
	}
	public void setReplyId(String replyId) {
		this.replyId = replyId;
	}
	
	
	
}
