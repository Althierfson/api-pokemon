package com.pokemonreview.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.pokemonreview.api.models.UserEntity;
import com.pokemonreview.api.repository.UserRepository;
import com.pokemonreview.api.service.AuthService;

import org.springframework.security.core.userdetails.UserDetails;

@Component
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;

    
    public String getAuthenticatedToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            var user = authentication.getPrincipal();
            if (user instanceof UserDetails) {
                return ((UserDetails)user).getUsername();
            }
        }
        return null;
    }

    @Override
    public UserEntity getAuthenticatedUserId() throws UsernameNotFoundException {
        String userName = getAuthenticatedToken();
        if (userName != null) {
            return userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User JWT not fould"));
        } else {
            throw new UsernameNotFoundException("User JWT not fould");
        }
    }
}
