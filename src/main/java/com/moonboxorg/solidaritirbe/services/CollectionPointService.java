package com.moonboxorg.solidaritirbe.services;

import com.moonboxorg.solidaritirbe.dto.CollectionPointResponseDTO;
import com.moonboxorg.solidaritirbe.dto.CreateCollectionPointRequestDTO;
import org.apache.coyote.BadRequestException;

import java.util.List;
import java.util.Optional;

public interface CollectionPointService {

    List<CollectionPointResponseDTO> getAll();

    Optional<CollectionPointResponseDTO> getCollectionPointByCode(Long code);

    List<CollectionPointResponseDTO> getCollectionPointsByProvinceCode(String provinceCode);

    List<CollectionPointResponseDTO> getCollectionPointsByProvinceName(String provinceName);

    List<CollectionPointResponseDTO> getCollectionPointsByProvinceRegion(String regionName);

    CollectionPointResponseDTO createCollectionPoint(CreateCollectionPointRequestDTO cpDTO) throws BadRequestException;
}
