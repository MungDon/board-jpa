package com.example.ex.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRequsetDto {

	@NotEmpty(message = "회원명은 필수입니다.")
	private String userName;
	
	@NotEmpty(message = "비밀번호 입력은 필수 입니다.")
	private String password;
	
	@NotEmpty(message = "비밀번호 확인 입력은 필수 입니다.")
	private String password2;
	
	@NotEmpty(message = "이메일은 필수 입니다.")
	private String email;
	
	@NotEmpty(message = "성별은 필수입니다.")
	private String gender;
	
	@NotEmpty(message = "국가는 필수 선택입니다.")
	private String country;
	
	@NotEmpty(message = "취미는 필수 선택입니다")
	private List<String> hobbies;
	

}
