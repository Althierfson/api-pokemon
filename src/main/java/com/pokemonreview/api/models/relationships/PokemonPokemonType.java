package com.pokemonreview.api.models.relationships;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.pokemonreview.api.models.Pokemon;
import com.pokemonreview.api.models.PokemonType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class PokemonPokemonType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "pokemon_id", nullable = false)
    private Pokemon pokemon;

    @ManyToOne
    @JoinColumn(name = "pokemon_type_id", nullable = false)
    private PokemonType pokemonType;
}
