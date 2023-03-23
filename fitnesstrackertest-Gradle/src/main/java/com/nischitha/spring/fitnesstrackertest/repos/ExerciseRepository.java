package com.nischitha.spring.fitnesstrackertest.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nischitha.spring.fitnesstrackertest.entities.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {
	
	@Query("select DISTINCT(e.exerciseCategory) from Exercise e")
	public List<String> findDistinctExerciseCategories();
	
	@Query("select e.exerciseName from Exercise e where e.exerciseCategory=:category ")
	public List<String> findListOfExercises(@Param("category")String category);

}
