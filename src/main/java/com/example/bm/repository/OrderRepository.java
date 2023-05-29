package com.example.bm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bm.modal.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
