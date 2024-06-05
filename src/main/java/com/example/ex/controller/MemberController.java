package com.example.ex.controller;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ex.dto.request.MemberRequsetDto;
import com.example.ex.dto.request.ReqMemberModify;
import com.example.ex.dto.request.UpdatePassword;
import com.example.ex.dto.response.ResMemberDetail;
import com.example.ex.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class MemberController {

	private final MemberService memberService;
	
	/*회원가입폼*/
	@GetMapping("/signup")
	public String signupForm(MemberRequsetDto memberRequsetDto) {
		return "signup";
	}
	
	/*회원가입*/
	@PostMapping("/signup")
	public String signup(@Valid MemberRequsetDto requset,BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return"signup";
		}
		boolean isUser = this.memberService.findByUser(requset);
		if(isUser) {
			bindingResult.reject("signupFailed","이미등록된 사용자입니다");
			return "signup" ;
		}
		boolean result = this.memberService.signup(requset);
		if(!result) {
			 bindingResult.rejectValue("password2", "passwordIncorrect", "2개의 비밀번호가 일치하지않습니다");
			 return "signup";
		}
		return "redirect:/user/signin";
	}
	
	/*로그인*/
	@GetMapping("/signin")
	public String signinForm() {
		return "signin";
	}
	
	/*회원상세보기*/
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/detail")
	public String memberdetail(Principal principal,Model model) {
		ResMemberDetail member = this.memberService.memberDetail(principal);
		model.addAttribute("member", member);
		return "member_detail";
	}
	
	/*회원 수정폼*/
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify")
	public String memberModifyForm(Principal principal,ReqMemberModify reqMemberModify) {
		ResMemberDetail member = this.memberService.memberDetail(principal);
		reqMemberModify.bindingMemberData(member);
		return "member_modify";
	}
	
	/*회원수정*/
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify")
	public String memberModify(@Valid ReqMemberModify reqMemberModify, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "redirect:/user/modify";
		}
		this.memberService.memberUpdate(reqMemberModify);
		return "redirect:/user/detail";
	}
	
	
	/*회원 탈퇴*/
	@PreAuthorize("isAuthenticated()")
	@DeleteMapping("/delete")
	public void memberDelete(@RequestParam(value = "member_sid")Long member_sid) {
		this.memberService.memberDelete(member_sid);
	}
	
	/*비번 변경*/
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/change/pw")
	public String changePwForm() {
		return "change_pw";
	}
	
	/*회원 비밀번호 변경*/
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/change/pw")
	public String changePw(Principal principal,UpdatePassword req) {
		this.memberService.changePw(principal,req);
		return "redirect:/user/detail";
	}
}
