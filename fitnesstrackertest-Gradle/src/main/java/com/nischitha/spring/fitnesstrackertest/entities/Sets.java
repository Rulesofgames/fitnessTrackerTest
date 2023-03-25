package com.nischitha.spring.fitnesstrackertest.entities;

import java.util.Date;
import java.util.Set;

import org.hibernate.annotations.CascadeType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
	private int minutes;
	private int distance;
	@Column(name="kcal")
	private int Kcal;
	private String notes;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="workout_id")
	private Workout workout;
	@ManyToOne
	@JoinColumn(name="exercise_id")
	private Exercise exercise;
	

}
