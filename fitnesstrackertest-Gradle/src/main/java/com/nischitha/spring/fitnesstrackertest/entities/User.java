package com.nischitha.spring.fitnesstrackertest.entities;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String email;
	private String password;
	@Column(name="firstname")
	private String firstName;
	@Column(name="lastname")
	private String lastName;
	/*@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)*/
	@Column(name="birthdate")
	private LocalDate DOB;
	private String address;
	private int pincode;
	private String country;
	private String gender;
	@OneToMany(mappedBy="user")
	@JsonManagedReference
	private Set<Workout> workout;
	
}
