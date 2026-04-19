package com.ktdsuniversity.edu.replies.vo.response;

import java.util.List;

import com.ktdsuniversity.edu.replies.vo.RepliesVO;

public class SearchResultVO {
	
	private List<RepliesVO> result;
	private int count;
	
	public List<RepliesVO> getResult() {
		return this.result;
	}
	public void setResult(List<RepliesVO> result) {
		this.result = result;
	}
	public int getCount() {
		return this.count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	
}
