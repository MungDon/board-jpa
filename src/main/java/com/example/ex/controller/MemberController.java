package com.example.ex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ex.dto.request.MemberRequsetDto;
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
	
	@GetMapping("/signin")
	public String signinForm() {
		return "signin";
	}
	
	
}
