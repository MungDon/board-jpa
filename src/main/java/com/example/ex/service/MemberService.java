package com.example.ex.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ex.dto.request.MemberRequsetDto;
import com.example.ex.dto.request.ReqMemberModify;
import com.example.ex.dto.request.UpdatePassword;
import com.example.ex.dto.response.ResMemberDetail;
import com.example.ex.entity.Hobby;
import com.example.ex.entity.Member;
import com.example.ex.exception.DataNotFoundException;
import com.example.ex.repository.HobbyRepository;
import com.example.ex.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final HobbyRepository hobbyRepository;
	
	/*회원가입*/
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
	
	/*기존유저인지 체크*/
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
	
	/*회원 상세정보*/
	@Transactional(readOnly = true)
	public ResMemberDetail memberDetail(Principal principal) {
		Member memberEntity = this.memberRepository.findByUserName(principal.getName()).orElseThrow(() -> new DataNotFoundException("회원정보가 없습니다"));
		List<Hobby> hobbyEntity = this.hobbyRepository.findAllByMember(memberEntity);
	
		List<String> hobby  = new ArrayList<String>();
		for(Hobby hobbies : hobbyEntity) {
			hobby.add(hobbies.getHobby());
		}
		return new ResMemberDetail(memberEntity, hobby);
	}
	
	/*회원정보수정*/
	@Transactional
	public void memberUpdate(ReqMemberModify req) {
		Member memberEntity = this.memberRepository.findByUserName(req.getUserName()).orElseThrow(()-> new DataNotFoundException("회원정보가 없어요"));
		memberEntity.update(req);
		this.hobbyRepository.deleteByMember(memberEntity);
		for(String  hobby : req.getHobbies()) {
			Hobby hobbyEntity = Hobby.builder()
										  .member(memberEntity)
										  .hobby(hobby)
										  .build();
			this.hobbyRepository.save(hobbyEntity);
		}
	}
	
	/*회원탈퇴*/
	@Transactional
	public void memberDelete(Long member_sid) {
		Member memberEntity =  this.memberRepository.findById(member_sid).orElseThrow(() -> new DataNotFoundException("회원정보가 없어요"));
		memberEntity.delete();
	}
	
	/*비밀번호 변경*/
	@Transactional
	public void changePw(Principal principal, UpdatePassword req) {
		Member member = this.memberRepository.findByUserName(principal.getName()).orElseThrow(() -> new DataNotFoundException("없는 회원"));
		if(passwordEncoder.matches(req.getCurrentPassword(), member.getPassword())) {
			member.updatePassword(passwordEncoder.encode(req.getNewPassword()));
		}else {
		throw new DataNotFoundException("없는비번임");
		}
	}
	
	
}
