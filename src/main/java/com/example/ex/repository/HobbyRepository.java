package com.example.ex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ex.entity.Hobby;
import com.example.ex.entity.Member;


public interface HobbyRepository extends JpaRepository<Hobby,Long>{
	
	List<Hobby> findAllByMember(Member member);	// 회원정보로 취미 찾기
	void deleteByMember(Member member);	// 회원정보로 취미 지우기
}
