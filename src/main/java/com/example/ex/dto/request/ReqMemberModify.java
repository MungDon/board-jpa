package com.example.ex.dto.request;

import java.util.List;

import com.example.ex.dto.response.ResMemberDetail;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqMemberModify {
	
	private String userName;
	
	@NotEmpty(message = "이메일은 필수 입니다.")
	private String email;
	@NotEmpty(message = "국가는 필수 선택입니다.")
	private String country;
	
	@NotEmpty(message = "취미는 필수 선택입니다")
	private List<String> hobbies;
	
	public void bindingMemberData(ResMemberDetail data) { // DB 에서 가져온 정보 생성자로 저장
		this.userName = data.getUserName();
		this.email = data.getEmail();
		this.country = data.getCountry();
		this.hobbies = data.getHobbies();
	}
}
