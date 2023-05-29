package com.example.bm.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.example.bm.enums.UserCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {
	
	private long id;
	
	private String email;
	
	private String userName;

	private String firstName;

	private String lastName;

	@Enumerated(EnumType.STRING)
	private UserCategory category;

}
