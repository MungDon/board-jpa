package com.example.ex.dto.response;

import java.util.List;

import com.example.ex.entity.Member;

import lombok.Getter;

@Getter
public class ResMemberDetail {
	
	private Long member_sid; // 회원 시퀀스

	private String userName;	// 회원명
	
	private String email;		// 이메일	
	
	private String gender;		// 성별
	
	private String country;		// 국적	
	
	private List<String> hobbies;		// 취미
	
	public ResMemberDetail(Member entity,List<String> hobbies) {// DB 에서 가져온 정보 생성자로 저장
		this.userName = entity.getUserName();
		this.email = entity.getEmail();
		this.gender = entity.getGender();
		this.country = entity.getCountry();
		this.hobbies = hobbies;
	}
}
