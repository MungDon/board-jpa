package com.example.ex.dto.response;

import com.example.ex.entity.Board;

import lombok.Getter;

@Getter
public class ResBoardList {
	
	private Long board_sid;	// 게시글 번호

	private String title;		// 제목
	
	private String writer;	// 작성자
	
	public ResBoardList(Board entity) {// DB 에서 가져온 정보 생성자로 저장
		this.board_sid = entity.getBoardSid();
		this.title = entity.getTitle();
		this.writer = entity.getMember().getUserName();
	}
}
