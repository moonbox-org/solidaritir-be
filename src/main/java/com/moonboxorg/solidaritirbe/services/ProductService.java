package com.moonboxorg.solidaritirbe.services;

import com.moonboxorg.solidaritirbe.dto.AddProductRequestDTO;
import com.moonboxorg.solidaritirbe.dto.ProductResponseDTO;
import com.moonboxorg.solidaritirbe.exceptions.ResourceNotFoundException;
import com.moonboxorg.solidaritirbe.models.GetFilteredProductsInputModel;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface ProductService {

    ProductResponseDTO getProductById(Long productId) throws ResourceNotFoundException;

    ProductResponseDTO getProductByEan13(String ean13) throws ResourceNotFoundException, BadRequestException;

    List<ProductResponseDTO> getFilteredProducts(GetFilteredProductsInputModel inputModel) throws ResourceNotFoundException, BadRequestException;

    ProductResponseDTO addProduct(AddProductRequestDTO dto) throws BadRequestException;
}
