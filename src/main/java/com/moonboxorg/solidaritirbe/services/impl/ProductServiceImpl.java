package com.moonboxorg.solidaritirbe.services.impl;

import com.moonboxorg.solidaritirbe.dto.AddProductRequestDTO;
import com.moonboxorg.solidaritirbe.dto.ProductResponseDTO;
import com.moonboxorg.solidaritirbe.entities.ProductEntity;
import com.moonboxorg.solidaritirbe.exceptions.ResourceNotFoundException;
import com.moonboxorg.solidaritirbe.models.GetFilteredProductsInputModel;
import com.moonboxorg.solidaritirbe.repositories.CategoryRepository;
import com.moonboxorg.solidaritirbe.repositories.ContainerTypeRepository;
import com.moonboxorg.solidaritirbe.repositories.ProductRepository;
import com.moonboxorg.solidaritirbe.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private static final String PRODUCT_WITH_SAME_NAME_ALREADY_EXISTS_MSG = "A product with the same name already exists";

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ContainerTypeRepository containerTypeRepository;

    @Override
    public ProductResponseDTO getProductById(Long productId) throws ResourceNotFoundException {
        log.info("Fetching product by ID: {}", productId);
        return productRepository.findById(productId)
                .map(this::mapToProductResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found for id: " + productId));
    }

    @Override
    public ProductResponseDTO getProductByEan13(String ean13) throws ResourceNotFoundException {
        log.info("Fetching product by EAN-13 code: {}", ean13);
        return productRepository.findByEan13(ean13)
                .map(this::mapToProductResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found for EAN-13 code: " + ean13));
    }

    @Override
    public List<ProductResponseDTO> getFilteredProducts(GetFilteredProductsInputModel input) throws ResourceNotFoundException {
        log.info("Fetching filtered products: {}", input);
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

    @Override
    public ProductResponseDTO addProduct(AddProductRequestDTO dto) throws BadRequestException {
        log.info("Adding product: {}", dto);

        if (productRepository.existsByNameIgnoreCase(dto.getName().trim()))
            throw new BadRequestException(PRODUCT_WITH_SAME_NAME_ALREADY_EXISTS_MSG);

        var productToSave = new ProductEntity();
        productToSave.setName(dto.getName());
        productToSave.setDescription(dto.getDescription());
        productToSave.setBrandName(dto.getBrandName());
        productToSave.setManufacturer(dto.getManufacturer());
        productToSave.setActive(dto.isActive());
        productToSave.setEan13(dto.getEan13());
        if (dto.getCategoryId() != null)
            productToSave.setCategory(categoryRepository.findById(dto.getCategoryId()).orElse(null));
        if (dto.getContainerTypeId() != null)
            productToSave.setContainerType(containerTypeRepository.findById(dto.getContainerTypeId()).orElse(null));
        return mapToProductResponseDTO(productRepository.save(productToSave));
    }

    // ----- Helper methods -----

    private ProductResponseDTO mapToProductResponseDTO(ProductEntity p) {
        log.info("Mapping product entity to product response DTO: {}", p);
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
