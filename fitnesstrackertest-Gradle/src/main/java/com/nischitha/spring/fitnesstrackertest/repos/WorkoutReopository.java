package com.nischitha.spring.fitnesstrackertest.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nischitha.spring.fitnesstrackertest.entities.Workout;

public interface WorkoutReopository extends JpaRepository<Workout, Integer> {

}
