package com.ktdsuniversity.edu.files.vo.request;

import java.util.List;

public class SearchFileGroupVO {
	private String fileGroupId;
	private List<Integer> deleteFileNum;
	
	public String getFileGroupId() {
		return this.fileGroupId;
	}
	public void setFileGroupId(String fileGroupId) {
		this.fileGroupId = fileGroupId;
	}
	public List<Integer> getDeleteFileNum() {
		return this.deleteFileNum;
	}
	public void setDeleteFileNum(List<Integer> deleteFileNum) {
		this.deleteFileNum = deleteFileNum;
	}
	
	
}
