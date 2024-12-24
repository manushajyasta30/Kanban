package com.assignment.kanban.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.assignment.kanban.entity.Stage;

@Repository
public interface StageRepository extends JpaRepository<Stage, Long> {

	@Query("FROM Stage s ORDER BY s.id ASC ")
	public List<Stage> findAll();

	public String findByName(String name);
	
	 
}
