package com.vw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vw.model.ProjectDetails;

@Repository
public interface AcceptanceRepository extends JpaRepository<ProjectDetails, Integer> {

	@Query(value = "SELECT * FROM project_details p WHERE p.agrmnt_Number = :agrmntNumber AND MONTH(p.generated_Date) = :month", nativeQuery = true)
	ProjectDetails findByAgrmntNumberAndGeneratedDate(@Param(value = "agrmntNumber") String agrmntNumber,
			@Param(value = "month") int month);

	@Query(value = "SELECT DISTINCT p.agrmnt_number FROM project_details p WHERE p.brand = :brand", nativeQuery = true)
	List<String> findAgreementNumbersByBrand(@Param("brand") String brand);
	
	@Query(value = "SELECT * FROM project_details p WHERE p.agrmnt_Number = :agrmntNumber AND MONTH(p.generated_Date) = :month AND p.location = :location", nativeQuery = true)
	ProjectDetails findByAgrmntNumberAndGeneratedDateAndLocation(@Param(value = "agrmntNumber") String agrmntNumber,
			@Param(value = "month") int month, @Param(value = "location") String location);
	
	@Query(value = "SELECT DISTINCT p.agrmnt_number FROM project_details p WHERE p.brand = :brand AND p.location = :location", nativeQuery = true)
	List<String> findAgreementNumbersByBrandLocation(@Param("brand") String brand, @Param("location") String location);
	
}  