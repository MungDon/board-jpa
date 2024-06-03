package com.example.ex.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberRole {
	ADMIN("1","관리자"),
	MEMBER("2", "일반회원");
	
	private String code;
	private String name;
	
}
