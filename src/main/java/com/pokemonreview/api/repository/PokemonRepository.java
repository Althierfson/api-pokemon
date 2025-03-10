package com.pokemonreview.api.repository;

import com.pokemonreview.api.models.Pokemon;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {
    @Query("SELECT p FROM Pokemon p JOIN p.pokemonTypes t WHERE t.pokemonType.id = :idType")
    Page<Pokemon> findByType(@Param("idType") int idType, Pageable pageable);
}
