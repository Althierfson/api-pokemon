package com.pokemonreview.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.pokemonreview.api.dto.PaginationResponse;
import com.pokemonreview.api.dto.PokemonTypeDTO;
import com.pokemonreview.api.exceptions.PokemonTypeNotFoundException;
import com.pokemonreview.api.models.PokemonType;
import com.pokemonreview.api.repository.PokemonTypeRepository;
import com.pokemonreview.api.service.PokemonTypeService;

@Service
public class PokemonTypeServiceImpl implements PokemonTypeService {
    @Autowired
    private PokemonTypeRepository repository;

    @Override
    public PokemonTypeDTO createType(PokemonTypeDTO dto) {
        PokemonType type = mapToEntity(dto);

        PokemonType newType = repository.save(type);


        return mapToDTO(newType);
    }

    @Override
    public PokemonTypeDTO getTypeById(int id) {
        PokemonType type = repository.findById(id)
            .orElseThrow(() -> new PokemonTypeNotFoundException("Type of pokemon with id " + id));
        return mapToDTO(type);
    }

    @Override
    public PaginationResponse<PokemonTypeDTO> getTypes(int pageNo, int pageSize) {
        Page<PokemonType> page = repository.findAll(PageRequest.of(pageNo, pageSize));
        List<PokemonTypeDTO> types = page.map(this::mapToDTO).toList();

        PaginationResponse<PokemonTypeDTO> response = new PaginationResponse<PokemonTypeDTO>();
        response.setPageNo(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLast(page.isLast());
        response.setContent(types);

        return response;
    }

    @Override
    public void deleteType(int id) {
        PokemonType type = repository.findById(id)
            .orElseThrow(() -> new PokemonTypeNotFoundException("Type of pokemon with id " + id));
        repository.delete(type);
    }
    
    PokemonType mapToEntity(PokemonTypeDTO dto) {
        return PokemonType.builder()
            .id(dto.getId())
            .name(dto.getName())
            .build();
    }

    PokemonTypeDTO mapToDTO(PokemonType dto) {
        return PokemonTypeDTO.builder()
            .id(dto.getId())
            .name(dto.getName())
            .build();
    }
}
