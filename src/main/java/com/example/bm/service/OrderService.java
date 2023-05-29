package com.example.bm.service;

import com.example.bm.dto.OrderRequestDTO;
import com.example.bm.dto.OrderResponseDTO;

public interface OrderService {

	OrderResponseDTO createOrder(OrderRequestDTO requestDTO);

}
