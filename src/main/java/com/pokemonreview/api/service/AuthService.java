package com.pokemonreview.api.service;

import com.pokemonreview.api.models.UserEntity;

public interface AuthService {
    public UserEntity getAuthenticatedUserId();
} 
