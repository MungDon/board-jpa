package com.example.ex.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ex.entity.Member;



public interface MemberRepository extends JpaRepository<Member, Long>{

	Optional<Member> findByEmail(String email);
	Optional<Member> findByUserName(String userName);
	
	
}
