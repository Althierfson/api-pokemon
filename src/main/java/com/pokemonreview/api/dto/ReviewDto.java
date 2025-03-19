package com.pokemonreview.api.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {
    private int id;
    private String title;
    private String content;
    private int stars;
    private Optional<String> userName;
}
