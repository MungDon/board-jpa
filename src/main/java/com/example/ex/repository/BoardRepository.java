package com.example.ex.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ex.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{

}
