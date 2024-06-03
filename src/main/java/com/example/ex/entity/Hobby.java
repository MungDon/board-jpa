package com.example.ex.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Hobby {

	@ManyToOne
	private Member member; 
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long hobbySid;
	
	private String hobby;
	
	@Builder
	public Hobby(Member member,String hobby) {
		this.member = member;
		this.hobby = hobby;
	}
}
