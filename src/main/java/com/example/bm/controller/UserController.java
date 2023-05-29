package com.example.bm.controller;

import java.util.List;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bm.dto.UpdateUserRequestDTO;
import com.example.bm.dto.UserRequestDTO;
import com.example.bm.dto.UserResponseDTO;
import com.example.bm.service.UserService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;

	private static final Logger logger = LogManager.getLogger(UserController.class.getName());

	@PostMapping(value = "/create-user")
	public UserResponseDTO createUser(@RequestBody UserRequestDTO userRequestDTO) {
		logger.info("Inside UserController-->createUser");
		return userService.createUser(userRequestDTO);
	}

	@PutMapping(value = "/update-user")
	public UserResponseDTO updateUser(@RequestBody UpdateUserRequestDTO updateUserRequestDTO) {
		logger.info("Inside UserController-->updateUser");
		return userService.updateUser(updateUserRequestDTO);
	}

	@GetMapping(value = "/get-users")
	public UserResponseDTO getUser(@RequestParam Long id) {
		logger.info("Inside UserController-->getUser");
		return userService.readUser(id);
	}

	@DeleteMapping(value = "/delete-user")
	public String deleteUser(@RequestParam Long id) {
		logger.info("Inside UserController-->deleteUser-->{}", id);
		userService.deleteUser(id);
		return "deleteSuccessfully";
	}

}
