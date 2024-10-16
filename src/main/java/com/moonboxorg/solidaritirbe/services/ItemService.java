package com.moonboxorg.solidaritirbe.services;

import com.moonboxorg.solidaritirbe.dto.AddItemRequestDTO;
import com.moonboxorg.solidaritirbe.dto.ItemResponseDTO;
import com.moonboxorg.solidaritirbe.exceptions.ResourceNotFoundException;
import com.moonboxorg.solidaritirbe.models.GetFilteredItemsInputModel;

import java.util.List;

public interface ItemService {

    ItemResponseDTO getItemById(Long id) throws ResourceNotFoundException;

    List<ItemResponseDTO> getFilteredItems(GetFilteredItemsInputModel input) throws ResourceNotFoundException;

    ItemResponseDTO addItem(AddItemRequestDTO addItemRequestDTO) throws ResourceNotFoundException;

    Long deleteItemById(Long id) throws ResourceNotFoundException;
}
