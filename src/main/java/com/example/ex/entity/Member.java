package com.example.ex.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access  = AccessLevel.PROTECTED)
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberSid;				// 회원 시퀀스
			
	private String userName;				// 회원명
	
	private String password;				// 비밀번호
	
	private String email;					// 이메일
	
	private String gender;					// 성별
	
	private String country;					// 국적
	
	private String deleteYn;				// 회원 탈퇴여부
	
	private LocalDateTime createdDate = LocalDateTime.now();	// 가입일시
	
	private LocalDateTime modifiedate;	// 회원정보 수정일시
	
	@OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
	private List<Hobby>hibbies;				// 취미
	
	@OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
	private List<Board>boards;				// 게시글
	
	@Builder	//setter 대신 값을 set 해줄 생성자 (builder 패턴)
	public Member(String userName,String password,String email,String gender, String country,String deleteYn) {
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.gender = gender;
		this.country = country;
		this.deleteYn = deleteYn;
	}

}
