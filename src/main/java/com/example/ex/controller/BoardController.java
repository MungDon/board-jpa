package com.example.ex.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ex.dto.request.ReqBoardAdd;
import com.example.ex.dto.response.ResBoardList;
import com.example.ex.service.BoardService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;                          
	
	/*게시글 목록*/
	@GetMapping("/list")
	public String main(Model model) {
		List<ResBoardList> list = this.boardService.boardList();
		model.addAttribute("list", list);
		return "board_list";
	}
	
	@GetMapping("/add")
	public String boardAddForm(ReqBoardAdd reqBoardAdd) {
		return"board_add";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/add")
	public String boardAdd(@Valid ReqBoardAdd req, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return"baord_add";
		}
		this.boardService.boardAdd(req);
		return"redirect:/board/list";
	}
}
