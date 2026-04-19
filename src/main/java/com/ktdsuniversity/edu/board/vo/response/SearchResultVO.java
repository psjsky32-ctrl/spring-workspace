package com.ktdsuniversity.edu.board.vo.response;

import java.util.List;

import com.ktdsuniversity.edu.board.vo.BoardVO;

/**
 * 게시글 검색 결과를 담고 있는 클래스.
 * 게시글 목록
 * 게시글 개수
 */
public class SearchResultVO {
	
	private List<BoardVO> result;
	private int Count;
	
	public List<BoardVO> getResult() {
		return this.result;
	}
	public void setResult(List<BoardVO> result) {
		this.result = result;
	}
	public int getCount() {
		return this.Count;
	}
	public void setCount(int count) {
		Count = count;
	}
}
