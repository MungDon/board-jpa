package com.example.ex.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ex.entity.Hobby;

public interface HobbyRepository extends JpaRepository<Hobby,Long>{

}
