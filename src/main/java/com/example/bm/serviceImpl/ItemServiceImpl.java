package com.example.bm.serviceImpl;

import java.util.Optional;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bm.Utils.Utils;
import com.example.bm.dto.ItemRequestDTO;
import com.example.bm.dto.ItemResponseDTO;
import com.example.bm.dto.UpdateItemRequestDTO;
import com.example.bm.enums.ItemCategory;
import com.example.bm.exception.CustomDataNotFoundException;
import com.example.bm.modal.Item;
import com.example.bm.repository.ItemRepository;
import com.example.bm.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemRepository itemRepository;

	String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
			+ "A-Z]{2,7}$";

	Pattern pat = Pattern.compile(emailRegex);

	private static final Logger logger = LogManager.getLogger(ItemServiceImpl.class.getName());

	@Override
	public ItemResponseDTO createItem(ItemRequestDTO request) {
		try {

			ItemCategory itemCategory = Utils.getItemCategory(request.getCategory());
			if (itemCategory == null) {
				throw new CustomDataNotFoundException("item category does not exists");
			}
			if (Utils.isNullOrEmpty(request.getName())) {
				throw new CustomDataNotFoundException("name is required");
			}
			if (Utils.isNullOrEmpty(request.getQuantity())) {
				throw new CustomDataNotFoundException("quantity is required");
			}
			if (Utils.isNullOrEmpty(request.getPrice())) {
				throw new CustomDataNotFoundException("price is required");
			}
			if (itemRepository.findByItemCode(request.getCode()).isPresent()) {
				logger.error("Inside UserServiceImpl-->createUser--username {}");
				throw new CustomDataNotFoundException("item code already exsists");
			}
			Item item = Item.builder().itemCode(request.getCode()).name(request.getName()).caterory(itemCategory)
					.price(Double.parseDouble(request.getPrice())).quantity(Double.parseDouble(request.getQuantity())).active(true).build();
			Item itemSaved = itemRepository.save(item);
			
			return ItemResponseDTO.builder().id(itemSaved.getId()).code(itemSaved.getItemCode()).name(itemSaved.getName()).category(itemSaved.getCaterory())
					.price(itemSaved.getPrice().toString()).quantity(itemSaved.getQuantity().toString()).build();

		} catch (Exception e) {
			logger.error("Inside UserServiceImpl-->createUser-->= inside catch: {}", e.getLocalizedMessage());
			throw new CustomDataNotFoundException((e.getLocalizedMessage()));
		}
	}

	@Override
	public ItemResponseDTO updateItem(UpdateItemRequestDTO updateData) {
		logger.info("Inside UserServiceImpl-->updateUser");
		Optional<Item> item = itemRepository.findById(updateData.getId());
		try {
			if (item.isEmpty()) {
				logger.error("Inside UserServiceImpl-->updateUser--NOItemFOUND {}");
				throw new CustomDataNotFoundException("no item found");
			}

			Item itemData = item.get();
			if (updateData.getCode() != null) {
				if (itemRepository.findByItemCode(updateData.getCode()).isPresent()
						&& !updateData.getCode().equals(itemData.getItemCode())) {
					logger.error("Inside UserServiceImpl-->updateItem-- {}");
					throw new CustomDataNotFoundException("code already present");
				}
				itemData.setItemCode(updateData.getCode());
			}

			if (!Utils.isNullOrEmpty(updateData.getName()))
				itemData.setName(updateData.getName());

			if (!Utils.isNullOrEmpty(updateData.getPrice()))
				itemData.setPrice(Double.parseDouble(updateData.getPrice()));
			
			if (!Utils.isNullOrEmpty(updateData.getQuantity()))
				itemData.setQuantity(Double.parseDouble(updateData.getQuantity()));

			
			if (!Utils.isNullOrEmpty(updateData.getCategory())) {
			ItemCategory itemCategory = Utils.getItemCategory(updateData.getCategory());
			if (itemCategory == null) {
				throw new CustomDataNotFoundException("item category does not exists");
			} else {
				itemData.setCaterory(itemCategory);	
			}
			}
			Item itemSaved = itemRepository.save(itemData);
			return ItemResponseDTO.builder().id(itemSaved.getId()).code(itemSaved.getItemCode()).name(itemSaved.getName()).category(itemSaved.getCaterory())
					.price(itemSaved.getPrice().toString()).quantity(itemSaved.getQuantity().toString()).build();

		} catch (Exception e) {
			logger.error("Inside UserServiceImpl-->updateUserRequestDTO-->updateUser-inside catch: {}",
					e.getLocalizedMessage());
			throw new CustomDataNotFoundException((e.getLocalizedMessage()));
		}
	}


	@Override
	public ItemResponseDTO readItem(long id) {
		logger.info("Inside UserServiceImpl-->readUser-id {}", id);
		try {
			Optional<Item> item = itemRepository.findById(id);
			if (!item.isPresent()) {
				logger.error("Inside UserServiceImpl-->readUser-id {}", id);
				throw new CustomDataNotFoundException("no user found");
			}

			Item data = item.get();
			return ItemResponseDTO.builder().id(data.getId()).code(data.getItemCode()).name(data.getName()).category(data.getCaterory())
					.price(data.getPrice().toString()).quantity(data.getQuantity().toString()).build();

		} catch (Exception e) {
			logger.error("Inside UserServiceImpl-->readUser-inside catch: {}", e.getLocalizedMessage());
			throw new CustomDataNotFoundException((e.getLocalizedMessage()));
		}
	}

	@Override
	public void deleteItem(long id) {
		logger.info("Inside UserServiceImpl-->deleteUser-id {}", id);
		try {
			Item item = itemRepository.findByIdAndActiveTrue(id);

			if (item == null) {
				logger.error("Inside UserServiceImpl-->deleteitem-id {}", id);
				throw new CustomDataNotFoundException("no item found");
			}
			item.setActive(false);
			itemRepository.save(item);
		} catch (Exception e) {
			logger.error("Inside UserServiceImpl-->deleteUser-inside catch {}", e.getLocalizedMessage());
			throw new CustomDataNotFoundException((e.getLocalizedMessage()));
		}
	}

	
}
