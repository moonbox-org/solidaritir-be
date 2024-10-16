package com.moonboxorg.solidaritirbe.services.impl;

import com.moonboxorg.solidaritirbe.dto.AddItemRequestDTO;
import com.moonboxorg.solidaritirbe.dto.ItemResponseDTO;
import com.moonboxorg.solidaritirbe.entities.ItemEntity;
import com.moonboxorg.solidaritirbe.exceptions.ResourceNotFoundException;
import com.moonboxorg.solidaritirbe.models.GetFilteredItemsInputModel;
import com.moonboxorg.solidaritirbe.repositories.CategoryRepository;
import com.moonboxorg.solidaritirbe.repositories.ItemRepository;
import com.moonboxorg.solidaritirbe.repositories.PackageRepository;
import com.moonboxorg.solidaritirbe.repositories.ProductRepository;
import com.moonboxorg.solidaritirbe.services.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private static final String ITEM_NOT_FOUND_MSG = "Item not found for ID: ";
    private static final String PRODUCT_NOT_FOUND_MSG = "Product not found for ID: ";
    private static final String CATEGORY_NOT_FOUND_MSG = "Category not found for ID: ";
    private static final String PACKAGE_NOT_FOUND_MSG = "Package not found for ID: ";

    private final ItemRepository itemRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final PackageRepository packageRepository;

    @Override
    public ItemResponseDTO getItemById(Long id) throws ResourceNotFoundException {
        return itemRepository.findById(id)
                .map(this::mapToItemResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException(ITEM_NOT_FOUND_MSG + id));
    }

    @Override
    public List<ItemResponseDTO> getFilteredItems(GetFilteredItemsInputModel input) throws ResourceNotFoundException {
        log.info("Fetching filtered items: {}", input);

        if (input.getProductId() != null) {
            if (!productRepository.existsById(input.getProductId()))
                throw new ResourceNotFoundException(PRODUCT_NOT_FOUND_MSG + input.getProductId());
            return itemRepository.findByProduct_Id(input.getProductId())
                    .stream()
                    .filter(item -> filterByExpirationDate(item, input))
                    .map(this::mapToItemResponseDTO)
                    .toList();
        }

        if (input.getCategoryId() != null) {
            if (!categoryRepository.existsById(input.getCategoryId()))
                throw new ResourceNotFoundException(CATEGORY_NOT_FOUND_MSG + input.getCategoryId());
            return itemRepository.findByProduct_Category_Id(input.getCategoryId())
                    .stream()
                    .filter(item -> filterByExpirationDate(item, input))
                    .map(this::mapToItemResponseDTO)
                    .toList();
        }

        if (input.getPackageId() != null) {
            if (!packageRepository.existsById(input.getPackageId()))
                throw new ResourceNotFoundException(PACKAGE_NOT_FOUND_MSG);
            return itemRepository.findByPackageEntity_Id(input.getPackageId())
                    .stream()
                    .filter(item -> filterByExpirationDate(item, input))
                    .map(this::mapToItemResponseDTO)
                    .toList();
        }

        return itemRepository.findAll()
                .stream()
                .filter(item -> filterByExpirationDate(item, input))
                .map(this::mapToItemResponseDTO)
                .toList();
    }

    @Override
    public ItemResponseDTO addItem(AddItemRequestDTO dto) throws ResourceNotFoundException {
        var packageEntity = dto.getPackageId() != null
                ? packageRepository.findById(dto.getPackageId()).orElseThrow(() -> new ResourceNotFoundException(PACKAGE_NOT_FOUND_MSG + dto.getPackageId()))
                : null;

        var productEntity = dto.getProductId() != null
                ? productRepository.findById(dto.getProductId()).orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUND_MSG + dto.getProductId()))
                : null;

        var itemToSave = new ItemEntity();
        itemToSave.setProduct(productEntity);
        itemToSave.setPackageEntity(packageEntity);
        itemToSave.setExpirationDate(dto.getExpirationDate());
        itemToSave = itemRepository.save(itemToSave);

        if (packageEntity != null) {
            packageEntity.getItems().add(itemToSave);
            packageRepository.save(packageEntity);
        }

        return mapToItemResponseDTO(itemToSave);
    }

    @Override
    public Long deleteItemById(Long id) throws ResourceNotFoundException {
        if (!itemRepository.existsById(id))
            throw new ResourceNotFoundException(ITEM_NOT_FOUND_MSG + id);
        itemRepository.deleteById(id);
        return id;
    }

    // ----- Helper methods ----- //

    /**
     * Filters an {@link ItemEntity} based on its expiration date and the number of days specified in the input.
     *
     * <p>If the input model contains a non-null value for {@code expInDays}, this method will check whether
     * the {@link ItemEntity}'s expiration date falls between the current date and the date that is {@code expInDays}
     * from today, inclusive of both boundaries.</p>
     *
     * <p>If the expiration date of the item is {@code null} or the item does not fall within the specified date range,
     * it will be filtered out. If {@code expInDays} is {@code null}, no filtering will be applied, and the method
     * will return {@code true}.</p>
     *
     * @param item  the {@link ItemEntity} being evaluated
     * @param input the {@link GetFilteredItemsInputModel} containing filtering parameters, including {@code expInDays}
     * @return {@code true} if the item's expiration date is within the specified range or if no expiration criteria
     * is provided, otherwise {@code false}
     */
    private boolean filterByExpirationDate(ItemEntity item, GetFilteredItemsInputModel input) {
        if (input.getExpInDays() != null) {
            var now = LocalDate.now();
            var upperBound = now.plusDays(input.getExpInDays());
            return item.getExpirationDate() != null
                    && (item.getExpirationDate().isEqual(now) || item.getExpirationDate().isAfter(now))
                    && (item.getExpirationDate().isEqual(upperBound) || item.getExpirationDate().isBefore(upperBound));
        }
        return true; // No filtering if no expiration criteria
    }

    private ItemResponseDTO mapToItemResponseDTO(ItemEntity item) {
        var itemResponseDTO = new ItemResponseDTO();
        itemResponseDTO.setId(item.getId());
        itemResponseDTO.setProductId(item.getProduct() != null ? item.getProduct().getId() : null);
        itemResponseDTO.setPackageId(item.getPackageEntity() != null ? item.getPackageEntity().getId() : null);
        itemResponseDTO.setExpirationDate(item.getExpirationDate());
        return itemResponseDTO;
    }
}