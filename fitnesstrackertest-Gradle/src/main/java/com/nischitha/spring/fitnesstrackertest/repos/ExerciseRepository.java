package com.nischitha.spring.fitnesstrackertest.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nischitha.spring.fitnesstrackertest.entities.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {

}
