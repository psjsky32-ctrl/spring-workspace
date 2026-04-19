	package com.ktdsuniversity.edu.replies.service;

import java.io.File;	
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.ktdsuniversity.edu.common.utils.AuthUtils;
import com.ktdsuniversity.edu.common.utils.ObjectUtils;
import com.ktdsuniversity.edu.exceptions.handlers.HelloSpringApiException;
import com.ktdsuniversity.edu.files.dao.FilesDao;
import com.ktdsuniversity.edu.files.vo.request.SearchFileGroupVO;
import com.ktdsuniversity.edu.files.vo.request.helpers.MultipartFileHandler;
import com.ktdsuniversity.edu.replies.dao.RepliesDao;
import com.ktdsuniversity.edu.replies.vo.RepliesVO;
import com.ktdsuniversity.edu.replies.vo.request.CreateVO;
import com.ktdsuniversity.edu.replies.vo.request.UpdateVO;
import com.ktdsuniversity.edu.replies.vo.response.DeleteResultVO;
import com.ktdsuniversity.edu.replies.vo.response.RecommendResultVO;
import com.ktdsuniversity.edu.replies.vo.response.SearchResultVO;
import com.ktdsuniversity.edu.replies.vo.response.UpdateResultVO;

@Service
public class RepliesServiceImpl implements RepliesService {

	private static final Logger logger = LoggerFactory.getLogger(RepliesServiceImpl.class);

	@Autowired
	private RepliesDao repliesDao;

	@Autowired
	private FilesDao filesDao;

	@Autowired
	private MultipartFileHandler multpartFileHandler;

	@Autowired
	private MultipartFileHandler multipartFileHandler;

	@Transactional
	@Override
	public RepliesVO createNewReply(CreateVO createVO) {

		String fileGroupId = this.multpartFileHandler.upload(createVO.getAttachFile());
		createVO.setFileGroupId(fileGroupId);

		int insertCount = this.repliesDao.insertNewReply(createVO);
		if (insertCount == 1) {
			RepliesVO insertResult = this.repliesDao.selectReplyByReplyId(createVO.getId());
			return insertResult;
		}
		return null;
	}

	@Override
	public SearchResultVO findRepliesByArticleId(String articleId) {

		SearchResultVO searchResultVO = new SearchResultVO();

		int count = this.repliesDao.selectRepliesCountByArticleId(articleId);
		searchResultVO.setCount(count);

		if (count > 0) {
			List<RepliesVO> searchList = this.repliesDao.selectRepliesByArticleId(articleId);
			searchResultVO.setResult(searchList);
		}
		return searchResultVO;
	}

	@Transactional
	@Override
	public DeleteResultVO deleteReplyById(String id) {

		RepliesVO reply = this.repliesDao.selectReplyByReplyId(id);
		if (ObjectUtils.isNotNull(reply)) {
			
			String loginEmail = AuthUtils.getUsername();
			boolean isAdminAccount = AuthUtils.hasAnyRole("RL-20260414-000001", "RL-20260414-000002");
			
			// 관리자가 아니고 내가 쓴것도 아니라면 댓글은 삭제할 수 없다.
			if (!isAdminAccount && !loginEmail.equals(reply.getEmail())) {
				throw new HelloSpringApiException(
						"권한이 부족합니다.", 
						HttpStatus.BAD_REQUEST.value(), 
						"자신의 댓글이 아닙니다.");
			}
		}

		int deleteCount = this.repliesDao.deleteReplyById(id);
		if (deleteCount == 1) {
			DeleteResultVO result = new DeleteResultVO();
			result.setId(id);
			return result;
		}
		return null;
	}

	@Transactional
	@Override
	public RecommendResultVO findRepliesByid(String id) {

		RepliesVO reply = this.repliesDao.selectReplyByReplyId(id);
		if (ObjectUtils.isNotNull(reply)) {
			
			String loginEmail = AuthUtils.getUsername();
			boolean isAdminAccount = AuthUtils.hasAnyRole("RL-20260414-000001", "RL-20260414-000003");
			
			if (isAdminAccount || loginEmail.equals(reply.getEmail())) {
				throw new HelloSpringApiException("권한이 부족합니다.", HttpStatus.BAD_REQUEST.value(), "자신의 댓글은 추천할 수 없습니다");
			}
		}

		int updateCount = this.repliesDao.updateRecommendCnt(id);
		if (updateCount == 1) {
			reply = this.repliesDao.selectReplyByReplyId(id);

			RecommendResultVO result = new RecommendResultVO();
			result.setId(id);
			result.setRecommendCnt(reply.getRecommendCnt());
			return result;
		}
		return null;
	}

	@Transactional
	@Override
	public UpdateResultVO updateReply(UpdateVO updateVO) {

		RepliesVO reply = this.repliesDao.selectReplyByReplyId(updateVO.getReplyId());
		if (ObjectUtils.isNotNull(reply)) {
			
			String loginEmail = AuthUtils.getUsername();
			boolean isAdminAccount = AuthUtils.hasAnyRole("RL-20260414-000001", "RL-20260414-000003");
			
			if (!isAdminAccount && !loginEmail.equals(reply.getEmail())) {
				throw new HelloSpringApiException("권한이 부족합니다.", HttpStatus.BAD_REQUEST.value(), "자신의 댓글이 아닙니다.");
			}
		}

		updateVO.setFileGroupId(reply.getFileGroupId());

		System.out.println("updateVO.getDelFileNum(): " + updateVO.getDelFileNum());
		if (updateVO.getDelFileNum() != null && updateVO.getDelFileNum().size() > 0) {

			SearchFileGroupVO searchFileGroupVO = new SearchFileGroupVO();
			System.out.println("updateVO.getDelFileNum(): " + updateVO.getDelFileNum());
			searchFileGroupVO.setDeleteFileNum(updateVO.getDelFileNum());
			searchFileGroupVO.setFileGroupId(updateVO.getFileGroupId());
			// 선택한 파일들의 정보를 조회 --> 파일의 경로가 필요해 --> 실제 파일 제거
			List<String> deleteTargets = this.filesDao.selectFilePathByFileGroupIdAndFileNums(searchFileGroupVO);
			for (String target : deleteTargets) {
				new File(target).delete();
			}
			// 선택한 파일들의 FILES 테이블에서 제거
			int deleteCount = this.filesDao.deleteFilesByFileGroupIdAndFileNums(searchFileGroupVO);
			logger.debug("삭제한 파일 데이터의 수 {}", deleteCount);
		}
		List<MultipartFile> attachFiles = updateVO.getNewAttachFile();

		// 첨부파일이 없는 게시글 수정
		// 없으면 filegroupId를 만들어
		String fileGroupId = updateVO.getFileGroupId();
		if (fileGroupId == null || fileGroupId.length() == 0) {
			fileGroupId = this.multipartFileHandler.upload(attachFiles);
			updateVO.setFileGroupId(fileGroupId);
		} else {
			// filegroupId가 잇으면 거기다 넣고
			String newFileGroupId = this.multipartFileHandler.upload(attachFiles, updateVO.getFileGroupId());
			updateVO.setFileGroupId(newFileGroupId);
		}

		int updateCount = this.repliesDao.updateReplyByReplyId(updateVO);

		UpdateResultVO result = new UpdateResultVO();
		result.setReplyId(updateVO.getReplyId());
		result.setUpdate(updateCount == 1);

		return result;
	}

}
