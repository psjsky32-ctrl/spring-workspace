package com.ktdsuniversity.edu.board.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ktdsuniversity.edu.board.enums.ReadType;
import com.ktdsuniversity.edu.board.service.BoardService;
import com.ktdsuniversity.edu.board.vo.BoardVO;
import com.ktdsuniversity.edu.board.vo.request.SearchListVO;
import com.ktdsuniversity.edu.board.vo.request.UpdateVO;
import com.ktdsuniversity.edu.board.vo.request.WriteVO;
import com.ktdsuniversity.edu.board.vo.response.SearchResultVO;
import com.ktdsuniversity.edu.exceptions.handlers.HelloSpringException;
import com.ktdsuniversity.edu.members.vo.MembersVO;

import jakarta.validation.Valid;

@Controller
public class BoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardService.class);
	
	/**
	 * 빈 컨테이너에 들어있는 객체 중 타입이 일치하는 객체를 할당 받는다.
	 */
	@Autowired
	private BoardService boardService;
	
	//http://192.168.211.11:8080/?pageNo=0&listSize=10 
	@GetMapping("/")
	public String viewListPage(Model model, SearchListVO searchListVO) {
		
		SearchResultVO searchResult = this.boardService.findAllBoard(searchListVO);
		
		// 게시글의 목록을 조회.
		List<BoardVO> list = searchResult.getResult();
		
		// 게시글의 개수 조회.
		int searchCount = searchResult.getCount();
		
		model.addAttribute("searchResult", list);
		model.addAttribute("searchCount", searchCount);
		
		model.addAttribute("pagenation", searchListVO);
		
		return "board/newlist";
	}
	
	// 게시글 등록 화면 보여주는 EndPoint
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/write")
	public String viewWritePage() {
		return "board/write";
	}

	
	// 게시글을 등록하는 EndPoint
	// form:form으로 데이터를 전송을 하면  @ModelAttribute springboot 3까지는 @Valid 뒤에 @ModelAttribute 
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/write")
	public String doWriteAction(@Valid  WriteVO writeVO, 
			BindingResult bindingRsult, Model model,
			Authentication authentication) {
		
		// 사용자의 입력값을 검증 했을 때, 에러가 있다면
		if(bindingRsult.hasErrors()) {
			// 브라우저에게 "board/write" 페이지를 보여주도록 하고
			// 해당 페이지에 사용자가 입력한 값을 전달한다.
			model.addAttribute("inputData", writeVO);
			return "board/write";
		}
		
		MembersVO loginUser = (MembersVO) authentication.getPrincipal();
		
		//서버가 기억하고있는 로그인 데이터(__LOGIN_DATA__)에서 로그인 한 사용자의 이메일을 가져온다.
		//서버가 기억하고있는 LOGIN_DATA중에 이메일만 writeVO에 넣어라?
		writeVO.setEmail(loginUser.getEmail());
		
		System.out.println(writeVO.getSubject());
		System.out.println(writeVO.getEmail());
		System.out.println(writeVO.getContent());
		// create, update, delete => 성공/실패 여부 반환.
		boolean createResult = this.boardService.createNewBoard(writeVO);
		logger.debug("게시글 생성 성공 {} ", createResult);
		
		// redirect: 브라우저에게 다음 End Point를 요청하도록 지시.
		// redirect:/ ==> 브라우저에게 "/" endpoint 로 이동하도록 지시.
		return "redirect:/";
	}
	
	
	// 게시글 내용 조회.
	// endpoint ==> /view/게시글아이디 예> /view/BO-20260327-000001
	// 해야 하는 역할
	//  1. 게시글 내용을 조회해서 브라우저에게 노출.
	//  2. 조회수 1증가.
	@GetMapping("/view/{articleId}")
	public String viewDetailPage(Model model, 
			@PathVariable String articleId) {
		
		// articleId로 데이터베이스에서 게시글을 조회한다.
		// 조회할 때 조회수가 하나 증가해야 한다.
		BoardVO findResult = this.boardService.findBoardByArticleId(articleId, ReadType.VIEW);
		
		model.addAttribute("article", findResult);
		
		return "board/view";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete")
	public String doDeleteAction(@RequestParam String id) {
		
		boolean deleteResult = this.boardService.deleteBoardByArticleId(id);
		logger.debug("삭제 결과 {} ", deleteResult);
		return "redirect:/";
		
	}
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/update/{articleId}")
	public String viewUpdatePage(@PathVariable String articleId, Model model,
			Authentication authentication) {
		BoardVO data = this.boardService.findBoardByArticleId(articleId, ReadType.UPDATE);
		model.addAttribute("article", data);
		
		MembersVO loginUser = (MembersVO) authentication.getPrincipal();
		
		if(!loginUser.getEmail().equals(data.getEmail())) {
			//TODO 게시글의 이메일과 세션의 이메일을 비교할 떄에는
			//항상 ServiceImpl에서 수행한다.
			// 403번 권한 없음
			throw new HelloSpringException("잘못된 접근입니다.", "errors/403");
			
		}
		
		return "board/update";
	}
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/update/{articleId}")
	public String doUpdateAction(@PathVariable String articleId,
			UpdateVO updateVO, 
			Authentication authentication) {
		
		MembersVO loginUser = (MembersVO) authentication.getPrincipal();
		updateVO.setEmail(loginUser.getEmail());
		
		boolean updateResult = this.boardService.updateBoardByArticleId(updateVO);
		logger.debug("수정 성공 {}", updateResult);
		
		return "redirect:/view/" + articleId;
	}
	
}