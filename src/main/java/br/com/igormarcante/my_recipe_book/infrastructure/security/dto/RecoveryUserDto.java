package br.com.igormarcante.my_recipe_book.infrastructure.security.dto;

import br.com.igormarcante.my_recipe_book.domain.entity.Role;

import java.util.List;

public record RecoveryUserDto(Long id,
                              String email,
                              List<Role> roles) {
}
