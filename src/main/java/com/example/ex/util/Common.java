package com.example.ex.util;

import org.springframework.validation.BindingResult;

public class Common {
	// 시간남으면 공통으로 뽑을 수 있는건 뽑아보려했던 클래스 시간 부족으로 실패
	public static String catchErrors(BindingResult bindingResult, String url) {
		if(bindingResult.hasErrors()) {
			return url;
		}
		return null;
	}
}
