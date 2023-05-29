package com.example.bm.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.example.bm.enums.ItemCategory;
import com.example.bm.enums.UserCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemResponseDTO {
	
	private long id;
	
	private String name;
	
	private String code;

	private String price;

	private String quantity;

	@Enumerated(EnumType.STRING)
	private ItemCategory category;

}
