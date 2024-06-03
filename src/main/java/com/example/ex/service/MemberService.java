package com.example.ex.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ex.dto.request.MemberRequsetDto;
import com.example.ex.entity.Hobby;
import com.example.ex.entity.Member;
import com.example.ex.repository.HobbyRepository;
import com.example.ex.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final HobbyRepository hobbyRepository;
	
	@Transactional
	public boolean signup(MemberRequsetDto requset) {
		boolean result = false;
		if(requset.getPassword().equals(requset.getPassword2())) {
			Member memberEntity = Member.builder()
																.userName(requset.getUserName())
																.password(passwordEncoder.encode(requset.getPassword()))
																.email(requset.getEmail())
																.gender(requset.getGender())
																.country(requset.getCountry())
																.deleteYn("N")
																.build();
			Member member = this.memberRepository.save(memberEntity);
	
			for(String  hobby : requset.getHobbies()) {
				Hobby hobbyEntity = Hobby.builder()
											  .member(member)
											  .hobby(hobby)
											  .build();
				this.hobbyRepository.save(hobbyEntity);
			}
			result = true;
		}
		return result;
	}
	
	@Transactional(readOnly = true)
	public boolean findByUser(MemberRequsetDto requset) {
		boolean result = false;
		Optional<Member> email = this.memberRepository.findByEmail(requset.getEmail());
		Optional<Member> userName = this.memberRepository.findByUserName(requset.getUserName());
		if(email.isPresent()||userName.isPresent()) {
			result=true;
			return result;
		}
		return result;
	}
}
