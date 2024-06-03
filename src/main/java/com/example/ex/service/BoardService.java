package com.example.ex.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ex.dto.request.ReqBoardAdd;
import com.example.ex.dto.response.ResBoardList;
import com.example.ex.entity.Board;
import com.example.ex.entity.Member;
import com.example.ex.exception.DataNotFoundException;
import com.example.ex.repository.BoardRepository;
import com.example.ex.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	
	private final BoardRepository boardRepository;
	private final MemberRepository memberRepository;
	
	/*게시글 작성*/
	@Transactional
	public void boardAdd(ReqBoardAdd req) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Optional<Member> member = this.memberRepository.findByUserName(authentication.getName());
		if(member.isEmpty()) {
			throw new DataNotFoundException("없는 작성자에요");
		}
		Board boardEntity = Board.builder()
												 .title(req.getTitle())
												 .content(req.getContent())
												 .member(member.get())
												 .deleteYn("N")
												 .build();
		this.boardRepository.save(boardEntity);
	}
	
	/*게시글 목록*/
	@Transactional(readOnly = true)
	public List<ResBoardList> boardList(){
		List<Board> list = this.boardRepository.findAll();
		return list.stream().map(ResBoardList::new).collect(Collectors.toList());
	}
		
	
}
