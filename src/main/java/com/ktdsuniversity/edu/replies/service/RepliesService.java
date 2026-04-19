package com.ktdsuniversity.edu.replies.service;

import com.ktdsuniversity.edu.replies.vo.RepliesVO;
import com.ktdsuniversity.edu.replies.vo.request.CreateVO;
import com.ktdsuniversity.edu.replies.vo.request.UpdateVO;
import com.ktdsuniversity.edu.replies.vo.response.DeleteResultVO;
import com.ktdsuniversity.edu.replies.vo.response.RecommendResultVO;
import com.ktdsuniversity.edu.replies.vo.response.SearchResultVO;
import com.ktdsuniversity.edu.replies.vo.response.UpdateResultVO;

import jakarta.validation.Valid;

public interface RepliesService {

	RepliesVO createNewReply(CreateVO createVO);

	SearchResultVO findRepliesByArticleId(String articleId);

	DeleteResultVO deleteReplyById(String id);

	RecommendResultVO findRepliesByid(String id);

	UpdateResultVO updateReply(UpdateVO updateVO);



}
