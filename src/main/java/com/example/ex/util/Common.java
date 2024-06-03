package com.example.ex.util;

import org.springframework.validation.BindingResult;

public class Common {

	public static String catchErrors(BindingResult bindingResult, String url) {
		if(bindingResult.hasErrors()) {
			return url;
		}
		return null;
	}
}
