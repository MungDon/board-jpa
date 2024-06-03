package com.example.ex.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqBoardAdd {
	
	@NotEmpty(message = "제목은 필수 입니다")
	private String title;
	
	@NotEmpty(message = "내용은 필수 입니다")
	private String content;
	
}
