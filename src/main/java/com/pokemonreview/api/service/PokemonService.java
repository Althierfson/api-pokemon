package com.pokemonreview.api.service;

import com.pokemonreview.api.dto.PokemonDto;
import com.pokemonreview.api.dto.PaginationResponse;

public interface PokemonService {
    PokemonDto createPokemon(PokemonDto pokemonDto);
    PaginationResponse<PokemonDto> getAllPokemon(int pageNo, int pageSize, int idType);
    PokemonDto getPokemonById(int id);
    PokemonDto updatePokemon(PokemonDto pokemonDto, int id);
    void deletePokemonId(int id);
}
