package com.nischitha.spring.fitnesstrackertest.repos;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nischitha.spring.fitnesstrackertest.entities.Workout;

public interface WorkoutReopository extends JpaRepository<Workout, Integer> {
	
	List<Workout> findByUserId(Integer userId,Sort sort);
	
	@Query(value="select date,SUM(duration) from workout w WHERE DATEDIFF(CURDATE(), `date`)<=:timeframe AND w.date<=CURDATE() AND w.user_id=:userId GROUP BY date",nativeQuery=true)
	public List<Object[]> findDuration(@Param("timeframe")Integer timeframe,@Param("userId")Integer userId);
	
	@Query(value="SELECT date, COUNT(*) as totalSets\r\n"
			+ "FROM workout w\r\n"
			+ "JOIN sets s ON w.id = s.workout_id\r\n"
			+ "where  DATEDIFF(CURDATE(), `date`) <=:timeframe AND w.date<=CURDATE() AND w.user_id=:userId \r\n"
			+ "GROUP BY date;",nativeQuery=true)
	public List<Object[]> findTotalSets(@Param("timeframe")Integer timeframe,@Param("userId")Integer userId);
	

	@Query(value="SELECT date, SUM(s.minutes)\r\n"
			+ "FROM workout w\r\n"
			+ "JOIN sets s ON w.id = s.workout_id\r\n"
			+ "where  DATEDIFF(CURDATE(), `date`) <=:timeframe AND w.date<=CURDATE() AND w.user_id=:userId \r\n"
			+ "GROUP BY date;",nativeQuery=true)
	public List<Object[]> findTotalMinutes(@Param("timeframe")Integer timeframe,@Param("userId")Integer userId);
	
	@Query(value="SELECT date, SUM(s.reps)\r\n"
			+ "FROM workout w\r\n"
			+ "JOIN sets s ON w.id = s.workout_id\r\n"
			+ "where  DATEDIFF(CURDATE(), `date`) <=:timeframe AND w.date<=CURDATE() AND w.user_id=:userId \r\n"
			+ "GROUP BY date;",nativeQuery=true)
	public List<Object[]> findTotalReps(@Param("timeframe")Integer timeframe,@Param("userId")Integer userId);
	
	@Query(value="SELECT date, SUM(s.weight)\r\n"
			+ "FROM workout w\r\n"
			+ "JOIN sets s ON w.id = s.workout_id\r\n"
			+ "where  DATEDIFF(CURDATE(), `date`) <=:timeframe AND w.date<=CURDATE() AND w.user_id=:userId \r\n"
			+ "GROUP BY date;",nativeQuery=true)
	public List<Object[]> findTotalWeight(@Param("timeframe")Integer timeframe,@Param("userId")Integer userId);
	
	@Query(value="SELECT date, SUM(s.distance)\r\n"
			+ "FROM workout w\r\n"
			+ "JOIN sets s ON w.id = s.workout_id\r\n"
			+ "where  DATEDIFF(CURDATE(), `date`) <=:timeframe AND w.date<=CURDATE() AND w.user_id=:userId \r\n"
			+ "GROUP BY date;",nativeQuery=true)
	public List<Object[]> findTotalDistance(@Param("timeframe")Integer timeframe,@Param("userId")Integer userId);
	
	@Query(value="SELECT date, SUM(s.kcal)\r\n"
			+ "FROM workout w\r\n"
			+ "JOIN sets s ON w.id = s.workout_id\r\n"
			+ "where  DATEDIFF(CURDATE(), `date`) <=:timeframe AND w.date<=CURDATE() AND w.user_id=:userId \r\n"
			+ "GROUP BY date;",nativeQuery=true)
	public List<Object[]> findTotalKcal(@Param("timeframe")Integer timeframe,@Param("userId")Integer userId);
	
	
	/*@Query(value="",nativeQuery=true)
	public List<Object[]> findKcal(@Param("timeframe")Integer timeframe);*/
	
	/*@Query(value="SELECT * FROM workout WHERE DATEDIFF(CURDATE(), `date`) <=180;",nativeQuery=true)
	public List<Workout> findLastSixMonths();*/

}
