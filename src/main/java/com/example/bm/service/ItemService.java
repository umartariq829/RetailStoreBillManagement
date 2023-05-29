package com.example.bm.service;

import com.example.bm.dto.ItemRequestDTO;
import com.example.bm.dto.ItemResponseDTO;
import com.example.bm.dto.UpdateItemRequestDTO;

public interface ItemService {

	ItemResponseDTO createItem(ItemRequestDTO requestDTO);

	ItemResponseDTO updateItem(UpdateItemRequestDTO requestDTO);

	// Page<UserResponseDTO> getAll(Integer page);

	ItemResponseDTO readItem(long id);

	void deleteItem(long id);

}
