package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
	LocalDate min;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}
	
	public Page<SaleMinDTO> searchReport(String minDate, String maxDate, String name, Pageable pageable){
		validateDate(minDate, maxDate);
		if (name.isEmpty()) {
			name = "";
		}
		Page<SaleMinDTO> result = repository.searchReport(min, today, name, pageable);
		return result;
	}
	
	public Page<SummaryDTO> searchSummary(String minDate, String maxDate, Pageable pageable){
		validateDate(minDate, maxDate);
		Page<SummaryDTO> result = repository.searchSummary(min, today, pageable);
		return result;
	}
	
	private void validateDate(String minDate, String maxDate) { 
		if (!maxDate.isEmpty()) {
			today = LocalDate.parse(maxDate);
		}
		if (minDate.isEmpty()) {
			min = today.minusYears(1L);
		} else {
			min = LocalDate.parse(minDate);
		}
	}
}
