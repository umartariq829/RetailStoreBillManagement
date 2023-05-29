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

import com.example.bm.dto.ItemRequestDTO;
import com.example.bm.dto.ItemResponseDTO;
import com.example.bm.dto.UpdateItemRequestDTO;
import com.example.bm.dto.UpdateUserRequestDTO;
import com.example.bm.dto.UserRequestDTO;
import com.example.bm.dto.UserResponseDTO;
import com.example.bm.service.ItemService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/item")
public class ItemController {
	
	
	@Autowired
	private ItemService itemService;

	private static final Logger logger = LogManager.getLogger(OrderController.class.getName());

	
	@PostMapping(value = "/create-item")
	public ItemResponseDTO createItem(@RequestBody ItemRequestDTO requestDTO) {
		logger.info("Inside OrderController-->createUser");
		return itemService.createItem(requestDTO);
	}

	@PutMapping(value = "/update-item")
	public ItemResponseDTO updateItem(@RequestBody UpdateItemRequestDTO requestDTO) {
		logger.info("Inside OrderController-->updateItem");
		return itemService.updateItem(requestDTO);
	}

	@GetMapping(value = "/get-item")
	public ItemResponseDTO getItem(@RequestParam Long id) {
		logger.info("Inside OrderController-->getUser");
		return itemService.readItem(id);
	}
	
	@DeleteMapping(value = "/delete-item")
	public String deleteItem(@RequestParam Long id) {
		logger.info("Inside OrderController-->deleteUser-->{}", id);
		itemService.deleteItem(id);
		return "deleteSuccessfully";
	}
	
//	@GetMapping(value = "/get-all-item")
//	public List<UserResponseDTO> getAllItem(
//			@RequestParam(value = "page", defaultValue = "1", required = false) Integer page) {
//		logger.info("Inside OrderController-->getAllUsers");
//		return itemService.getAll(page).getContent();
//		
//	}
	

}
