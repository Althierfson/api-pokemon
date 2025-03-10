package com.pokemonreview.api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import javax.persistence.*;

import com.pokemonreview.api.models.relationships.PokemonPokemonType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class PokemonType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @OneToMany(mappedBy = "pokemonType")
    private List<PokemonPokemonType> pokemonTypes;
}
