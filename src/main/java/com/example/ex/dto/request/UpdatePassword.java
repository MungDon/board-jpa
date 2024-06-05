package com.example.ex.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePassword {

	private String currentPassword;	// 현재 비밀번호
	
	private String newPassword;		// 바꿀 비밀번호
}
