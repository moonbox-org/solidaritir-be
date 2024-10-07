package com.moonboxorg.solidaritirbe.services.impl;

import com.moonboxorg.solidaritirbe.dto.ProductResponseDTO;
import com.moonboxorg.solidaritirbe.entities.ProductEntity;
import com.moonboxorg.solidaritirbe.exceptions.ResourceNotFoundException;
import com.moonboxorg.solidaritirbe.models.GetFilteredProductsInputModel;
import com.moonboxorg.solidaritirbe.repositories.CategoryRepository;
import com.moonboxorg.solidaritirbe.repositories.ProductRepository;
import com.moonboxorg.solidaritirbe.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductResponseDTO getProductById(Long productId) throws ResourceNotFoundException {
        return productRepository.findById(productId)
                .map(this::mapToProductResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found for id: " + productId));
    }

    @Override
    public ProductResponseDTO getProductByEan13(String ean13) throws ResourceNotFoundException {
        return productRepository.findByEan13(ean13)
                .map(this::mapToProductResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found for EAN-13 code: " + ean13));
    }

    @Override
    public List<ProductResponseDTO> getFilteredProducts(GetFilteredProductsInputModel input) throws ResourceNotFoundException {
        if (input.getName() != null) {
            var products = input.isActive()
                    ? productRepository.findByNameContainingIgnoreCaseAndActive(input.getName(), true)
                    : productRepository.findByNameContainingIgnoreCase(input.getName());
            return products.stream()
                    .map(this::mapToProductResponseDTO)
                    .toList();
        }

        if (input.getCategoryId() != null) {
            if (!categoryRepository.existsById(input.getCategoryId()))
                throw new ResourceNotFoundException("Category not found for id: " + input.getCategoryId());

            var products = input.isActive()
                    ? productRepository.findByCategory_IdAndActive(input.getCategoryId(), true)
                    : productRepository.findByCategory_Id(input.getCategoryId());
            return products.stream()
                    .map(this::mapToProductResponseDTO)
                    .toList();
        }

        return (input.isActive() ? productRepository.findAllByActive(true) : productRepository.findAll())
                .stream()
                .map(this::mapToProductResponseDTO)
                .toList();
    }

    // ----- Helper methods -----

    private ProductResponseDTO mapToProductResponseDTO(ProductEntity p) {
        var productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId(p.getId());
        productResponseDTO.setName(p.getName());
        productResponseDTO.setDescription(p.getDescription());
        productResponseDTO.setCategoryId(p.getCategory() != null ? p.getCategory().getId() : null);
        productResponseDTO.setCategoryName(p.getCategory() != null ? p.getCategory().getName() : null);
        productResponseDTO.setActive(p.isActive());
        productResponseDTO.setEan13(p.getEan13());
        productResponseDTO.setBrandName(p.getBrandName());
        productResponseDTO.setManufacturer(p.getManufacturer());
        return productResponseDTO;
    }
}
