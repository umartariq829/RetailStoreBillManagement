package com.example.bm.serviceImpl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bm.dto.OrderRequestDTO;
import com.example.bm.dto.OrderResponseDTO;
import com.example.bm.dto.UserResponseDTO;
import com.example.bm.enums.ItemCategory;
import com.example.bm.enums.UserCategory;
import com.example.bm.exception.CustomDataNotFoundException;
import com.example.bm.modal.Item;
import com.example.bm.modal.Order;
import com.example.bm.modal.User;
import com.example.bm.repository.ItemRepository;
import com.example.bm.repository.OrderRepository;
import com.example.bm.repository.UserRepository;
import com.example.bm.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private OrderRepository orderRepository;

	private static final Logger logger = LogManager.getLogger(UserServiceImpl.class.getName());

	
	@Override
	public OrderResponseDTO createOrder(OrderRequestDTO requestDTO) {
		
		logger.info("Inside OrderServiceImpl-->createOrder");
		
		Optional<User> user = userRepository.findById(requestDTO.getUserId());
		try {
			if (user.isEmpty()) {
				logger.error("Inside OrderServiceImpl-->createOrder--NOUSERFOUND {}", user);
				throw new CustomDataNotFoundException("no user found");
			}
			
			if (requestDTO.getItemids() == null || requestDTO.getItemids().isEmpty()) {
				logger.error("Inside OrderServiceImpl-->createOrder--NOITEMSFOUND {}", user);
				throw new CustomDataNotFoundException("no items found");
			}
			List<Item> itemList = new ArrayList<>(); 
			Double groceryPrice = 0d;
			Double nonGroceryPrice = 0d;
			
			for(Long item: requestDTO.getItemids()) {
				Optional<Item> itemFetched = itemRepository.findById(item);
				if(itemFetched.isPresent()) {
					if(itemFetched.get().getCaterory().equals(ItemCategory.GROCERY)) {
						groceryPrice = groceryPrice + (itemFetched.get().getPrice()
						* itemFetched.get().getQuantity());
					} else {
						nonGroceryPrice = nonGroceryPrice + (itemFetched.get().getPrice()
						* itemFetched.get().getQuantity());
					}
					itemList.add(itemFetched.get());
				}
			}
			
		UserResponseDTO userResponseDTO =
		UserResponseDTO.builder().id(user.get().getId()).email(user.get().getEmail())
		.firstName(user.get().getFirstName()).lastName(user.get().getLastName())
		.category(user.get().getCaterory()).userName(user.get().getUserName()).build();
		
		
		 long difference_In_Time
         = new Date().getTime() -  user.get().getCreatedDate().getTime();
		
		 
		  long difference_In_Years
          = (difference_In_Time
             / (1000l * 60 * 60 * 24 * 365));


		if(user.get().getCaterory().equals(UserCategory.EMPLOYEE)) {
			nonGroceryPrice = nonGroceryPrice -(nonGroceryPrice * 0.30) ;
		} else if (user.get().getCaterory().equals(UserCategory.AFFILATED)) {
			nonGroceryPrice = nonGroceryPrice - (nonGroceryPrice * 0.10);	
		} else if(user.get().getCaterory().equals(UserCategory.CUSTOMER) && difference_In_Years >= 2l) {
			nonGroceryPrice =  nonGroceryPrice - (nonGroceryPrice * 0.10);	
		}
		
		Double totalPrice = nonGroceryPrice + groceryPrice;
		int totalPricee = (int) (totalPrice / 100);
		Double totalAmount = totalPrice-(totalPricee*5);
		Order order = Order.builder().active(true).totalAmount(totalAmount).item(itemList).user(user.get()).build();
		
		order.setCreatedDate(new Date());
		Order orderSaved = orderRepository.save(order);	
		
		return OrderResponseDTO.builder().id(orderSaved.getId()).userResponseDTO(userResponseDTO).totalAmount(totalAmount).build();
	
	} catch (Exception e) {
		logger.error("Inside OrderServiceImpl--inside catch: {}",
				e.getLocalizedMessage());
		throw new CustomDataNotFoundException((e.getLocalizedMessage()));
	}
	
}
}