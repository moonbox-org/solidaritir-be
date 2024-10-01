package com.moonboxorg.solidaritirbe.services.impl;

import com.moonboxorg.solidaritirbe.dto.CollectionPointResponseDTO;
import com.moonboxorg.solidaritirbe.dto.CreateCollectionPointRequestDTO;
import com.moonboxorg.solidaritirbe.entities.CollectionPointEntity;
import com.moonboxorg.solidaritirbe.entities.ProvinceEntity;
import com.moonboxorg.solidaritirbe.repositories.CollectionPointRepository;
import com.moonboxorg.solidaritirbe.repositories.ProvinceRepository;
import com.moonboxorg.solidaritirbe.services.CollectionPointService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CollectionPointServiceImpl implements CollectionPointService {

    private final CollectionPointRepository collectionPointRepository;
    private final ProvinceRepository provinceRepository;

    public List<CollectionPointResponseDTO> getFilteredCollectionPoints(String provCode, String provName, String regName) {
        if (Strings.isNotBlank(provCode)) {
            return getCollectionPointsByProvinceCode(provCode);
        }
        if (Strings.isNotBlank(provName)) {
            return getCollectionPointsByProvinceName(provName);
        }
        if (Strings.isNotBlank(regName)) {
            return getCollectionPointsByProvinceRegion(regName);
        }
        return getAll();
    }

    @Override
    public List<CollectionPointResponseDTO> getAll() {
        return collectionPointRepository.findAll().stream()
                .map(this::mapToCollectionPointResponseDTO)
                .toList();
    }

    @Override
    public Optional<CollectionPointResponseDTO> getCollectionPointByCode(Long code) {
        return collectionPointRepository.getCollectionPointByCode(code)
                .map(this::mapToCollectionPointResponseDTO);
    }

    @Override
    public List<CollectionPointResponseDTO> getCollectionPointsByProvinceCode(String provinceCode) {
        return collectionPointRepository.getCollectionPointsByProvinceCode(provinceCode).stream()
                .map(this::mapToCollectionPointResponseDTO)
                .toList();
    }

    @Override
    public List<CollectionPointResponseDTO> getCollectionPointsByProvinceName(String provinceName) {
        return collectionPointRepository.getCollectionPointsByProvinceName(provinceName).stream()
                .map(this::mapToCollectionPointResponseDTO)
                .toList();
    }

    @Override
    public List<CollectionPointResponseDTO> getCollectionPointsByProvinceRegion(String regionName) {
        return collectionPointRepository.getCollectionPointsByProvinceRegion(regionName).stream()
                .map(this::mapToCollectionPointResponseDTO)
                .toList();
    }

    @Override
    public CollectionPointResponseDTO createCollectionPoint(CreateCollectionPointRequestDTO dto) throws BadRequestException {
        ProvinceEntity province = provinceRepository
                .findByCode(dto.getProvinceCode())
                .orElseThrow(ValidationException::new);

        CollectionPointEntity cp = new CollectionPointEntity();
        cp.setName(dto.getName());
        cp.setProvince(province);
        cp.setActive(dto.isActive());
        cp.setNotes(dto.getNotes());

        return mapToCollectionPointResponseDTO(collectionPointRepository.save(cp));
    }

    private CollectionPointResponseDTO mapToCollectionPointResponseDTO(CollectionPointEntity cp) {
        return new CollectionPointResponseDTO(
                cp.getCode(),
                cp.getName(),
                cp.getProvince().getCode(),
                cp.getProvince().getName(),
                cp.getProvince().getRegion(),
                cp.isActive(),
                cp.getNotes(),
                cp.getCreatedAt(),
                cp.getCreatedBy(),
                cp.getLastUpdatedAt(),
                cp.getLastUpdatedBy()
        );
    }
}
