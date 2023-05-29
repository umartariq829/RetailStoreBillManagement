package com.example.bm.dto;

import com.example.bm.enums.UserCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDTO {
	private long id;
	private UserResponseDTO userResponseDTO;
    private Double totalAmount;
}
