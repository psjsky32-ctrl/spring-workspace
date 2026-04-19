package com.ktdsuniversity.edu.exceptions.handlers.web;

import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *  /error 전용 컨트롤러 => 반드시 ErrorController를 통해서만 구현할 수 있다.
 */

@Controller
public class NoEndpointController implements ErrorController{

	
	@GetMapping("/error")
	public String viewNotFoundPage(Model model) {
		model.addAttribute("errorMessage", "존재하지 않는 URL입니다.");
		
		return "errors/404";
	}
}
