package com.example.ex.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ex.entity.Member;



public interface MemberRepository extends JpaRepository<Member, Long>{

	Optional<Member> findByEmail(String email);				// 이메일찾기
	Optional<Member> findByUserName(String userName); // 유저명 찾기
	
	
}
