package com.example.ex.controller;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.ex.dto.request.ReqBoardAdd;
import com.example.ex.dto.request.ReqBoardModify;
import com.example.ex.dto.response.ResBoardDetail;
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
	public String main(Model model,@RequestParam(value = "page",defaultValue = "1") int page) {
		Page<ResBoardList> pagingList = this.boardService.boardList(page);
		model.addAttribute("pagingList", pagingList);
		return "board_list";
	}
	
	/*게시글 등록폼*/
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/add")
	public String boardAddForm(ReqBoardAdd reqBoardAdd) {
		return"board_add";
	}
	
	/*게시글 등록*/
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/add")
	public String boardAdd(@Valid ReqBoardAdd req, BindingResult bindingResult, Principal principal) {
		if(bindingResult.hasErrors()) {
			return"baord_add";
		}
		this.boardService.boardAdd(req, principal);
		return"redirect:/board/list";
	}
	
	/*게시글 상세보기*/
	@GetMapping("/detail/{board_sid}")
	public String boardDetail(@PathVariable(value = "board_sid")Long board_sid,Model model) {
		ResBoardDetail detail = this.boardService.boardDetail(board_sid);
		model.addAttribute("detail", detail);
		return "board_detail";
	}
	
	/*게시글 수정폼*/
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{board_sid}")
	public String boardModifyForm(@PathVariable(value = "board_sid")Long board_sid,ReqBoardModify reqBoardModify) {
		ResBoardDetail data = this.boardService.boardDetail(board_sid);
		reqBoardModify.setBoard_sid(data.getBoard_sid());
		reqBoardModify.setContent(data.getContent());
		reqBoardModify.setTitle(data.getTitle());
		return "board_modify";
	}
	
	/*게시글 수정*/
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify")
	public String boardModify(@Valid ReqBoardModify req, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return String.format("redirect:/board/modify/%s",req.getBoard_sid());
		}
		this.boardService.boardModify(req);
		return String.format("redirect:/board/detail/%s",req.getBoard_sid());
	}
	
	/*게시글 삭제*/
	@DeleteMapping("/delete")
	@ResponseBody
	public void deleteBoard(@RequestParam(value = "board_sid")Long board_sid){
		this.boardService.boardDelete(board_sid);
	}
}
