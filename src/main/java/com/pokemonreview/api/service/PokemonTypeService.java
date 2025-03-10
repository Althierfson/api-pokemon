package com.pokemonreview.api.service;

import com.pokemonreview.api.dto.PaginationResponse;
import com.pokemonreview.api.dto.PokemonTypeDTO;

public interface PokemonTypeService {
    PokemonTypeDTO createType(PokemonTypeDTO dto);
    PokemonTypeDTO getTypeById(int id);
    PaginationResponse<PokemonTypeDTO> getTypes(int pageNo, int pageSize);
    void deleteType(int id);
} 
