package com.example.ex.dto.response;

import com.example.ex.entity.Board;

import lombok.Getter;

@Getter
public class ResBoardList {
	
	private Long board_sid;

	private String title;		// 제목
	
	private String writer;	// 작성자
	
	public ResBoardList(Board entity) {
		this.board_sid = entity.getBoardSid();
		this.title = entity.getTitle();
		this.writer = entity.getMember().getUserName();
	}
}
