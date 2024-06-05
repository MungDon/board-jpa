package com.example.ex.entity;

import java.time.LocalDateTime;

import com.example.ex.dto.request.ReqBoardModify;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long boardSid;			// 게시글 시퀀스
	
	private String title;				// 게시글 제목
	
	private String content;			// 게시글 내용
	
	@ManyToOne
	private Member member;		// 작성자
	
	private int hits;					// 조회수
	
	private String deleteYn;		// 삯제여부
	
	private LocalDateTime createdDate = LocalDateTime.now();	// 작성 일시
	
	private LocalDateTime modifiedDate;										// 수정 일시
	
	
	@Builder// 빌더패턴으로 정보저장
	public Board(String title, String content, Member member,String deleteYn) {
		this.title = title;
		this.content = content;
		this.member = member;
		this.deleteYn = deleteYn;
	}
	
	/*게시글 수정*/
	public void update(ReqBoardModify req) {
		this.title = req.getTitle();
		this.content = req.getContent();
		this.modifiedDate = LocalDateTime.now();
	}
	
	/*조회수 증가*/
	public void increaseHits() {
		this.hits++;
	}
	
	/*게시글 삭제(논리)*/
	public void delete() {
		this.deleteYn = "Y";
	}
}
