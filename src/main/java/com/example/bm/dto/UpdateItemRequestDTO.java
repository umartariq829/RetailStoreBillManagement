package com.example.bm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateItemRequestDTO {

	private long id;
	
	private String name;
	
	private String code;

	private String price;

	private String quantity;

	private String category;

}
