package com.devsuperior.dsmeta.dto;

import projections.SummaryProjection;

public class SummaryDTO {
	private String sellerName;
	private Double total;
	
	public SummaryDTO(String sellerName, Double total) {
		this.sellerName = sellerName;
		this.total = total;
	}
	
	public SummaryDTO(SummaryProjection projection) {
		sellerName = projection.getName();
		total = projection.getTotal();
	}
	
	
	public String getSellerName() {
		return sellerName;
	}

	public Double getTotal() {
		return total;
	}
	
	
}
