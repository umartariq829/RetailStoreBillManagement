package com.example.bm.controller;

import java.util.List;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bm.dto.OrderRequestDTO;
import com.example.bm.dto.OrderResponseDTO;
import com.example.bm.dto.UpdateUserRequestDTO;
import com.example.bm.dto.UserRequestDTO;
import com.example.bm.dto.UserResponseDTO;
import com.example.bm.service.ItemService;
import com.example.bm.service.OrderService;
import com.example.bm.service.UserService;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/order")
public class OrderController {
	
	
	@Autowired
	private OrderService orderService;

	private static final Logger logger = LogManager.getLogger(OrderController.class.getName());

	
	@PostMapping(value = "/create-order")
	public OrderResponseDTO createItem(@RequestBody OrderRequestDTO requestDTO) {
		logger.info("Inside OrderController-->createOrder");
		return orderService.createOrder(requestDTO);
	}

//	@PutMapping(value = "/update-item")
//	public UserResponseDTO updateItem(@RequestBody UpdateUserRequestDTO updateUserRequestDTO) {
//		logger.info("Inside OrderController-->updateUser");
//		return itemService.updateItem(updateUserRequestDTO);
//	}

//	@GetMapping(value = "/get-item")
//	public UserResponseDTO getItem(@RequestParam Long id) {
//		logger.info("Inside OrderController-->getUser");
//		return itemService.readItem(id);
//	}
//	
//	@DeleteMapping(value = "/delete-item")
//	public String deleteItem(@RequestParam Long id) {
//		logger.info("Inside OrderController-->deleteUser-->{}", id);
//		itemService.deleteItem(id);
//		return "deleteSuccessfully";
//	}
	
}
