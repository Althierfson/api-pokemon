package com.pokemonreview.api.controllers;


import com.pokemonreview.api.dto.PokemonDto;
import com.pokemonreview.api.dto.PaginationResponse;
import com.pokemonreview.api.service.PokemonService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "Pokemons")
@RequestMapping("/api/pokemon")
public class PokemonController {

    @Autowired
    private PokemonService pokemonService;

    @ApiOperation(value = "Lista pokemons de forma paginada")
    @GetMapping()
    public ResponseEntity<PaginationResponse<PokemonDto>> getPokemons(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "typeId", defaultValue = "0", required = false) int typeId
    ) {
        return new ResponseEntity<>(pokemonService
            .getAllPokemon(pageNo, pageSize, typeId), HttpStatus.OK);
    }

    @ApiOperation(value = "Buscar um  pokemons por Id")
    @GetMapping("{id}")
    public ResponseEntity<PokemonDto> pokemonDetail(@PathVariable int id) {
        return ResponseEntity.ok(pokemonService.getPokemonById(id));

    }

    @ApiOperation(value = "Criar um novo pokemons")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PokemonDto> createPokemon(@RequestBody PokemonDto pokemonDto) {
        return new ResponseEntity<>(pokemonService.createPokemon(pokemonDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Atualizar um pokemons especifico")
    @PutMapping("{id}")
    public ResponseEntity<PokemonDto> updatePokemon(@RequestBody PokemonDto pokemonDto, @PathVariable("id") int pokemonId) {
        PokemonDto response = pokemonService.updatePokemon(pokemonDto, pokemonId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Deletar um pokemons especifico")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePokemon(@PathVariable("id") int pokemonId) {
        pokemonService.deletePokemonId(pokemonId);
        return new ResponseEntity<>("Pokemon delete", HttpStatus.OK);
    }

}
