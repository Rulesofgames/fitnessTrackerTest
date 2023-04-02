package com.nischitha.spring.fitnesstrackertest.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nischitha.spring.fitnesstrackertest.entities.Workout;

public interface WorkoutReopository extends JpaRepository<Workout, Integer> {
	
	@Query(value="select date,SUM(duration) from workout GROUP BY date",nativeQuery=true)
	public List<Object[]> findDuration();
	
	@Query(value="SELECT * FROM workout WHERE DATEDIFF(CURDATE(), `date`) <=180;",nativeQuery=true)
	public List<Workout> findLastSixMonths();

}
