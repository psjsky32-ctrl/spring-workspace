package com.ktdsuniversity.edu.replies.vo;

import java.util.List;

import com.ktdsuniversity.edu.files.vo.FilesVO;
import com.ktdsuniversity.edu.members.vo.MembersVO;

public class RepliesVO {
	
	private String id;
	private String reply;
	private int recommendCnt;
	private String crtDt;
	private String mdfyDt;
	private String email;
	private String articleId;
	private String parentReplyId;
	private String fileGroupId;
	
	
	private MembersVO membersVO;
	
	private int level;
	private List<FilesVO> files;
	
	public int getLevel() {
		return this.level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public List<FilesVO> getFiles() {
		return this.files;
	}
	public void setFiles(List<FilesVO> files) {
		this.files = files;
	}
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReply() {
		return this.reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	public int getRecommendCnt() {
		return this.recommendCnt;
	}
	public void setRecommendCnt(int recommendCnt) {
		this.recommendCnt = recommendCnt;
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
	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getArticleId() {
		return this.articleId;
	}
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	public String getParentReplyId() {
		return this.parentReplyId;
	}
	public void setParentReplyId(String parentReplyId) {
		this.parentReplyId = parentReplyId;
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
