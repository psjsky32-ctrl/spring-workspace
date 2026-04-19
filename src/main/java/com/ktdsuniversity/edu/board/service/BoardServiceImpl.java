package com.ktdsuniversity.edu.board.service;

import java.io.File;		
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ktdsuniversity.edu.board.dao.BoardDao;
import com.ktdsuniversity.edu.board.enums.ReadType;
import com.ktdsuniversity.edu.board.vo.BoardVO;
import com.ktdsuniversity.edu.board.vo.request.SearchListVO;
import com.ktdsuniversity.edu.board.vo.request.UpdateVO;
import com.ktdsuniversity.edu.board.vo.request.WriteVO;
import com.ktdsuniversity.edu.board.vo.response.SearchResultVO;
import com.ktdsuniversity.edu.exceptions.handlers.HelloSpringException;
import com.ktdsuniversity.edu.files.dao.FilesDao;
import com.ktdsuniversity.edu.files.vo.request.SearchFileGroupVO;
import com.ktdsuniversity.edu.files.vo.request.UploadVO;
import com.ktdsuniversity.edu.files.vo.request.helpers.MultipartFileHandler;

@Service
public class BoardServiceImpl implements BoardService {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardServiceImpl.class);
	
	/**
	 * 빈 컨테이너에 들어있는 객체 중 타입이 일치하는 객체를 할당 받는다.
	 */
	@Autowired
	private BoardDao boardDao;

	@Autowired
	private MultipartFileHandler multipartFileHandler;

	@Autowired
	private FilesDao filesDao;

	@Override
	public SearchResultVO findAllBoard(SearchListVO searchListVO) {
		SearchResultVO result = new SearchResultVO();

		// 게시글 개수 조회. ==> 1
		int count = this.boardDao.selectBoardCount(searchListVO);
		result.setCount(count);

		//몇 개의 페이지가 필요한지 계산.
		searchListVO.computePagenation(count);
		
		if (count == 0) {
			return result;
		}

		// 게시글 목록 조회. ==> [BoardVO]
		List<BoardVO> list = this.boardDao.selectBoardList(searchListVO);
		result.setResult(list);

		return result;
	}

	@Transactional
	@Override
	public boolean createNewBoard(WriteVO writeVO) {
		// 첨부파일 업로드
		List<MultipartFile> attachFiles = writeVO.getAttachFile();
		//id 넣기
		String fileGroupId = this.multipartFileHandler.upload(attachFiles);
		writeVO.setFileGroupId(fileGroupId);
		// dao => insert 요청
		// mybatis 는 insert, update, delete를 수행했을 때
		// 영향을 받은 row의 수를 반환시킨다.
		// 예> insert ==> insert 된 row의 개수 반환.
		// update ==> update 된 row의 개수 반환.
		// delete ==> delete 된 row의 개수 반환.
		int insertCount = this.boardDao.insertNewBoard(writeVO);
		// TODD 트랜잭션 테스트 후 삭제.
//		Integer.parseInt("asdfasdfasfdasdf");
		
		//System IO ==> Application이 구동중에 출력 == CPU/RAM을 많이쓴다
		//System IO ==> CPU/RAM ?ㅅ?
		
		logger.debug("생성된 게시글의 개수? {}", insertCount);
//		System.out.println("생성된 게시글의 개수? " + insertCount);
		return insertCount == 1;
	}

	@Transactional
	@Override
	public BoardVO findBoardByArticleId(String articleId, ReadType readType) {
		if (readType == ReadType.VIEW) {
			// 1. 조회수 증가.
			int updateCount = this.boardDao.updateViewCntIncreaseById(articleId);
			logger.debug("조회수가 증가된 게시글의 수: {}", updateCount);

			if (updateCount == 0) {
				// 존재하지 않는 게시글을 조회하려 했다.
				throw new HelloSpringException("존재하지 않는 게시글입니다.", "errors/404");
			}
		}

		// 2. 게시글 조회.
		BoardVO board = this.boardDao.selectBoardById(articleId);

		// 조회한 게시글을 반환.
		return board;
	}

	@Transactional
	@Override
	public boolean deleteBoardByArticleId(String id) {
		int deleteCount = this.boardDao.deleteBoardById(id);
		// 삭제하려는 게시글에 첨부된 파일을 가져온다.
		List<String> filePaths = this.filesDao.selectFilePathByFileGroupId(id);
		// 파일 목록이 존재하면, 모든 파일들을 제거한다.
		if (filePaths != null && filePaths.size() > 0) {
			for (String path : filePaths) {
				new File(path).delete();
			}
			// 파일 목록을 제거한 이후에 "FILES" 테이블에서 해당 파일 정보를 모두 삭제한다.
			int deleteFileCount = this.filesDao.deleteFilesByFileGroupId(id);
			logger.debug("삭제된 파일 수 {}" , deleteFileCount);
		}
		return deleteCount == 1;
	}
	
	@Transactional
	@Override
	public boolean updateBoardByArticleId(UpdateVO updateVO) {
		logger.debug("qwer {}" , updateVO.getContent());
		// 선택한 파일들만 삭제.
		if (updateVO.getDeleteFileNum() != null && updateVO.getDeleteFileNum().size() > 0) {
			SearchFileGroupVO searchFileGroupVO = new SearchFileGroupVO();
			searchFileGroupVO.setDeleteFileNum(updateVO.getDelFileNum());
			searchFileGroupVO.setFileGroupId(updateVO.getFileGroupId());
			
			// 선택한 파일들의 정보를 조회 --> 파일의 경로가 필요해 --> 실제 파일 제거
			List<String> deleteTargets = this.filesDao.selectFilePathByFileGroupIdAndFileNums(searchFileGroupVO);
			for (String target : deleteTargets) {
				new File(target).delete();
			}
			// 선택한 파일들의 FILES 테이블에서 제거
			int deleteCount = this.filesDao.deleteFilesByFileGroupIdAndFileNums(searchFileGroupVO);
			logger.debug("삭제한 파일 데이터의 수 {}" , deleteCount);
		}
		// 첨부파일 업로드
		List<MultipartFile> attachFiles = updateVO.getAttachFile();
		
		//첨부파일이 없는 게시글 수정
		//없으면 filegroupId를 만들어
		String fileGroupId = updateVO.getFileGroupId();
		if(fileGroupId == null || fileGroupId.length() == 0) {
			fileGroupId = this.multipartFileHandler.upload(attachFiles);
			updateVO.setFileGroupId(fileGroupId);
		}else {
			//filegroupId가 잇으면 거기다 넣고
			this.multipartFileHandler.upload(attachFiles, updateVO.getFileGroupId());
		}
		this.multipartFileHandler.upload(attachFiles, updateVO.getFileGroupId());
		
		int updateCount = this.boardDao.updateBoardById(updateVO);
		
		return updateCount == 1;
	}

}