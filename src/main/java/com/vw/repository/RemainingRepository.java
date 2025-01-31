package com.vw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vw.model.RemainingData;

@Repository
public interface RemainingRepository extends JpaRepository<RemainingData, Integer> {

	@Query(value = "SELECT * FROM remaining_data p WHERE p.agrmnt_Number = :agrmntNumber", nativeQuery = true)
	RemainingData findRemData(@Param(value = "agrmntNumber") String agrmntNumber);
	
}  