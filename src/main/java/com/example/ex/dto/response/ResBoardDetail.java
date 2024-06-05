package com.example.ex.dto.response;

import java.time.LocalDateTime;

import com.example.ex.entity.Board;

import lombok.Getter;

@Getter
public class ResBoardDetail {
	
	private Long board_sid;	// 게시글 시퀀스

	private String title;			// 제목
	
	private String content;		// 내용
	
	private String writer;		// 작성자
	
	private int hits;				// 조회수
	
	private LocalDateTime createdDate;		// 게시글 생성일
	
	private LocalDateTime modifiedDate;	// 게시글 수정일
	
	public ResBoardDetail(Board entity) {	// DB 에서 가져온 정보 생성자로 저장
		this.board_sid = entity.getBoardSid();
		this.title = entity.getTitle();
		this.content = entity.getContent();
		this.writer = entity.getMember().getUserName();
		this.hits = entity.getHits();
		this.createdDate = entity.getCreatedDate();
		this.modifiedDate = entity.getModifiedDate();
	}
}
