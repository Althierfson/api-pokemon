package com.pokemonreview.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pokemonreview.api.dto.PaginationResponse;
import com.pokemonreview.api.dto.PokemonTypeDTO;
import com.pokemonreview.api.service.PokemonTypeService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/type")
public class PokemonTypeController {
    @Autowired
    private PokemonTypeService service;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PokemonTypeDTO> create(@RequestBody PokemonTypeDTO dto) {
        return new ResponseEntity<>(service.createType(dto), HttpStatus.CREATED);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PaginationResponse<PokemonTypeDTO>> getAllTypes(
        @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
        @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        return new ResponseEntity<>(service.getTypes(pageNo, pageSize), HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PokemonTypeDTO> getTypeById(@PathVariable("id") int id) {
        return new ResponseEntity<>(service.getTypeById(id), HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        service.deleteType(id);
        return new ResponseEntity<>("type delete", HttpStatus.OK);
    }
}
