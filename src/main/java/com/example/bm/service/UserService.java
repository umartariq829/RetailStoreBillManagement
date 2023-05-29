package com.example.bm.service;

import org.springframework.data.domain.Page;

import com.example.bm.dto.UpdateUserRequestDTO;
import com.example.bm.dto.UserRequestDTO;
import com.example.bm.dto.UserResponseDTO;

public interface UserService {
	
	UserResponseDTO createUser(UserRequestDTO userRequestDTO);
  
	UserResponseDTO updateUser(UpdateUserRequestDTO updateUserRequestDTO);

	UserResponseDTO readUser(long id);

	void deleteUser(long id);

}
