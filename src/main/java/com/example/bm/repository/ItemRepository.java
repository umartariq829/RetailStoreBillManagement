package com.example.bm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bm.modal.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

	Optional<Item> findByItemCode(String code);

	Item findByIdAndActiveTrue(long id);

}
