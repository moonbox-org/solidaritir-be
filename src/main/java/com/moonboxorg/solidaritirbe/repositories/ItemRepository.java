package com.moonboxorg.solidaritirbe.repositories;

import com.moonboxorg.solidaritirbe.entities.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
}
