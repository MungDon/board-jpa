package com.example.ex.service;

import java.security.Principal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ex.dto.request.ReqBoardAdd;
import com.example.ex.dto.request.ReqBoardModify;
import com.example.ex.dto.response.ResBoardDetail;
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
	public void boardAdd(ReqBoardAdd req, Principal principal) {
		Optional<Member> member = this.memberRepository.findByUserName(principal.getName());
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
	public Page<ResBoardList> boardList(int page){
		Pageable pageable = PageRequest.of(page-1, 10, Sort.by(Direction.DESC, "createdDate"));
		Page<Board> list = this.boardRepository.findAllByDeleteYn("N",pageable);
   
		Page<ResBoardList> pagingList = list.map(
                postPage -> new ResBoardList(postPage));
        return pagingList;
	}
	
	/*게시글 상세보기*/
	@Transactional(readOnly = true)
	public ResBoardDetail boardDetail(Long board_sid) {
		Board boardEntity = this.boardRepository.findById(board_sid).orElseThrow(() -> new DataNotFoundException("게시글정보가 없습니다"));
		boardEntity.increaseHits();
		return new ResBoardDetail(boardEntity);
	}
	
	/*게시글 수정*/
	@Transactional
	public void boardModify(ReqBoardModify req) {
		Board boarEntity = this.boardRepository.findById(req.getBoard_sid()).orElseThrow(() -> new DataNotFoundException("게시글 정보가 없습니다"));
		boarEntity.update(req);
	}
	
	/*게시글 삭제*/
	@Transactional
	public void boardDelete(Long board_sid) {
		Board boardEntity = this.boardRepository.findById(board_sid).orElseThrow(() -> new DataNotFoundException("게시글 정보가 없습니다"));
		boardEntity.delete();
	}
		
	
}
