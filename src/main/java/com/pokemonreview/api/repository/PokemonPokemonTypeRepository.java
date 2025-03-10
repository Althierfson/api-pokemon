package com.pokemonreview.api.repository;

import com.pokemonreview.api.models.relationships.PokemonPokemonType;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PokemonPokemonTypeRepository extends JpaRepository<PokemonPokemonType, Integer> {
    
}
