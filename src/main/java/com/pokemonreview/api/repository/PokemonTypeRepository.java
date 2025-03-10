package com.pokemonreview.api.repository;

// import java.util.List;
// import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pokemonreview.api.models.PokemonType;

public interface PokemonTypeRepository  extends JpaRepository<PokemonType, Integer>{
    // Optional<List<PokemonType>> findTypesByIds(List<Integer> ids);
}
