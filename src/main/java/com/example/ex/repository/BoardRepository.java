package com.example.ex.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ex.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{
	Page<Board> findAllByDeleteYn(String deleteYn,Pageable pageable); // 게시글 페이징 리스트
}
