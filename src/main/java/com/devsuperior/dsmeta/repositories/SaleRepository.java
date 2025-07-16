package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

	@Query(value = "SELECT obj FROM Sale obj JOIN FETCH obj.seller "
			+ "WHERE obj.date BETWEEN :minDate AND :maxDate AND UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name, '%'))",
			countQuery = "SELECT COUNT(obj) FROM Sale obj JOIN obj.seller")			
	Page<SaleMinDTO> searchReport(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable);
	
	
	@Query("SELECT new com.devsuperior.dsmeta.dto.SummaryDTO(obj.seller.name, SUM(obj.amount) AS total) FROM Sale obj INNER JOIN obj.seller "
			+ "WHERE obj.date BETWEEN :minDate AND :maxDate GROUP BY obj.seller.name")				
	Page<SummaryDTO> searchSummary(LocalDate minDate, LocalDate maxDate, Pageable pageable);
}
