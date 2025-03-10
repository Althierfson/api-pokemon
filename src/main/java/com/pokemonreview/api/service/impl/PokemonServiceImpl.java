package com.pokemonreview.api.service.impl;

import com.pokemonreview.api.dto.PokemonDto;
import com.pokemonreview.api.dto.PaginationResponse;
import com.pokemonreview.api.dto.PokemonTypeDTO;
import com.pokemonreview.api.exceptions.PokemonNotFoundException;
import com.pokemonreview.api.models.Pokemon;
import com.pokemonreview.api.models.PokemonType;
import com.pokemonreview.api.models.relationships.PokemonPokemonType;
import com.pokemonreview.api.repository.PokemonPokemonTypeRepository;
import com.pokemonreview.api.repository.PokemonRepository;
import com.pokemonreview.api.repository.PokemonTypeRepository;
import com.pokemonreview.api.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PokemonServiceImpl implements PokemonService {
    @Autowired
    private PokemonRepository pokemonRepository;
    @Autowired
    private PokemonTypeRepository pokemonTypeRepository;
    @Autowired
    private PokemonPokemonTypeRepository pokemonPokemonTypeRepository;

    @Override
    public PokemonDto createPokemon(PokemonDto pokemonDto) {
        Pokemon pokemon = mapToEntity(pokemonDto);
        Pokemon newPokemon = pokemonRepository.save(pokemon);

        List<PokemonPokemonType> types = pokemonDto.getTypes().stream()
            .map((e) -> {
                PokemonType type = pokemonTypeRepository.findById(e.getId())
                    .orElseThrow(() -> new RuntimeException("Pokemon type with id" + e.getId() + "not find"));
                new PokemonPokemonType();
                return PokemonPokemonType.builder()
                    .pokemon(newPokemon)
                    .pokemonType(type)
                    .build();
            }).toList();
        
        pokemonPokemonTypeRepository.saveAll(types);
        
        newPokemon.setPokemonTypes(types);
        return mapToDto(newPokemon);
    }

    @Override
    public PaginationResponse<PokemonDto> getAllPokemon(int pageNo, int pageSize, int idType) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Pokemon> pokemons;

        if (idType != 0) {
            pokemons = pokemonRepository.findByType(idType, pageable);
        } else {
            pokemons = pokemonRepository.findAll(pageable);
        }

        List<PokemonDto> pokemonsDTOs = pokemons.map(this::mapToDto).toList();

        PaginationResponse<PokemonDto> pokemonResponse = new PaginationResponse<PokemonDto>();
        pokemonResponse.setContent(pokemonsDTOs);
        pokemonResponse.setPageNo(pokemons.getNumber());
        pokemonResponse.setPageSize(pokemons.getSize());
        pokemonResponse.setTotalElements(pokemons.getTotalElements());
        pokemonResponse.setTotalPages(pokemons.getTotalPages());
        pokemonResponse.setLast(pokemons.isLast());

        return pokemonResponse;
    }

    @Override
    public PokemonDto getPokemonById(int id) {
        Pokemon pokemon = pokemonRepository.findById(id)
            .orElseThrow(() -> new PokemonNotFoundException("Pokemon could not be found"));
        return mapToDto(pokemon);
    }

    @Override
    public PokemonDto updatePokemon(PokemonDto pokemonDto, int id) {
        Pokemon pokemon = pokemonRepository.findById(id)
            .orElseThrow(() -> new PokemonNotFoundException("Pokemon could not be updated"));

        pokemon.setName(pokemonDto.getName());
        pokemon.setUrlImage(pokemonDto.getUrlImage());

        Pokemon updatedPokemon = pokemonRepository.save(pokemon);

        for (int i = 0; i < pokemon.getPokemonTypes().size(); i++) {
            System.out.println("deletar: " + pokemon.getPokemonTypes().get(i).getId());
            pokemonPokemonTypeRepository.deleteById(
                pokemon.getPokemonTypes().get(i).getId());
        }

        List<PokemonPokemonType> types = pokemonDto.getTypes().stream()
            .map((e) -> {
                PokemonType type = pokemonTypeRepository.findById(e.getId())
                    .orElseThrow(() -> new RuntimeException("Pokemon type with id" + e.getId() + "not find"));
                new PokemonPokemonType();
                return PokemonPokemonType.builder()
                    .pokemon(pokemon)
                    .pokemonType(type)
                    .build();
            }).toList();

        pokemonPokemonTypeRepository.saveAll(types);
        
        updatedPokemon.setPokemonTypes(types);
        return mapToDto(updatedPokemon);
    }

    @Override
    public void deletePokemonId(int id) {
        Pokemon pokemon = pokemonRepository.findById(id).orElseThrow(() -> new PokemonNotFoundException("Pokemon could not be delete"));
        
        for (int i = 0; i < pokemon.getPokemonTypes().size(); i++) {
            System.out.println("deletar: " + pokemon.getPokemonTypes().get(i).getId());
            pokemonPokemonTypeRepository.deleteById(
                pokemon.getPokemonTypes().get(i).getId());
        }

        pokemonRepository.delete(pokemon);
    }

    private PokemonDto mapToDto(Pokemon pokemon) {
        new PokemonTypeDTO();
        return PokemonDto.builder()
            .id(pokemon.getId())
            .name(pokemon.getName())
            .types(pokemon.getPokemonTypes().stream()
                .map((e) -> PokemonTypeDTO.builder()
                    .id(e.getPokemonType().getId())
                    .name(e.getPokemonType().getName())
                    .build()).toList())
            .urlImage(pokemon.getUrlImage())
            .build();
    }

    private Pokemon mapToEntity(PokemonDto dto) {
        return Pokemon.builder()
            .id(dto.getId())
            .name(dto.getName())
            .pokemonTypes(new ArrayList<>())
            .urlImage(dto.getUrlImage())
            .build();
    }

    // private PokemonTypeDTO mapPokemonTypeDTO(PokemonType type) {

    //     PokemonTypeDTO dto = new PokemonTypeDTO();
    //     dto.setId(type.getId());
    //     dto.setName(type.getName());
    //     return dto;
    // }

    // private PokemonType mapPokemonTypeDTOToPokemonType (PokemonTypeDTO dto) {
    //     return PokemonType.builder()
    //             .id(dto.getId())
    //             .name(dto.getName())
    //             .build();
    // }
}
