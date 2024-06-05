package com.example.ex.entity;

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
public class Hobby {

	@ManyToOne
	private Member member; 			// 회원정보
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long hobbySid;				// 취미 시퀀스
	
	private String hobby;				// 취미
	
	@Builder // 빌더패턴으로 정보저장
	public Hobby(Member member,String hobby) {
		this.member = member;
		this.hobby = hobby;
	}
	
}
