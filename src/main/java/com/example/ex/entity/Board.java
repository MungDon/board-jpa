package com.example.ex.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long boardSid;			// 게시글 시퀀스
	
	private String title;				// 게시글 제목
	
	private String content;			// 게시글 내용
	
	@ManyToOne
	private Member member;		// 작성자
	
	private String deleteYn;
	
	private LocalDateTime createdDate = LocalDateTime.now();	// 작성 일시
	
	private LocalDateTime modifiedDate;										// 수정 일시
	
	@Builder
	public Board(String title, String content, Member member,String deleteYn) {
		this.title = title;
		this.content = content;
		this.member = member;
		this.deleteYn = deleteYn;
	}
}
