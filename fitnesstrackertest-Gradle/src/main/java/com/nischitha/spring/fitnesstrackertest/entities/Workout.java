package com.nischitha.spring.fitnesstrackertest.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Workout {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private LocalDate date;
	@Column(name = "start_time")
	private LocalTime startTime;
	@Column(name = "end_time")
	private LocalTime endTime;
	private int bodyWeight;
	private String notes;
	private int duration;
	@Column(name="workouttype")
	private String workoutType;
	@ManyToOne
	@JoinColumn(name="user_id")
	 @JsonBackReference
	private User user;
	@OneToMany(mappedBy="workout",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JsonManagedReference
	private Set<Sets> sets;
	

}
