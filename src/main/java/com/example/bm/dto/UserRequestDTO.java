package com.example.bm.dto;

import java.util.List;

import com.example.bm.enums.UserCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDTO {
	
	private String email;
	
	private String userName;

	private String firstName;

	private String lastName;

	private String category;

}
