package com.ktdsuniversity.edu.board.vo.request;

import java.util.List;

public class UpdateVO extends WriteVO{
	//삭제대상 번호만 가지고 오겠다
	private List<Integer> deleteFileNum;

	private List<Integer> delFileNum;
	
	public List<Integer> getDeleteFileNum() {
		return this.deleteFileNum;
	}

	public void setDeleteFileNum(List<Integer> deleteFileNum) {
		this.deleteFileNum = deleteFileNum;
	}

	public List<Integer> getDelFileNum() {
		return this.delFileNum;
	}

	public void setDelFileNum(List<Integer> delFileNum) {
		this.delFileNum = delFileNum;
	}
	
	
	
}
