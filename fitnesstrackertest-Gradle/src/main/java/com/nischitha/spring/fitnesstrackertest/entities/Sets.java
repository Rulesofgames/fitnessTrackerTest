package com.nischitha.spring.fitnesstrackertest.entities;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Sets {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private int weight;
	private int reps;
	private String notes;
	@ManyToOne
	@JoinColumn(name="workout_id")
	private Workout workout;
	@ManyToOne
	@JoinColumn(name="exercise_id")
	private Exercise exercise;
	

}
