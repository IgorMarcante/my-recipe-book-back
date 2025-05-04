package br.com.igormarcante.my_recipe_book.infrastructure.security.dto;

import br.com.igormarcante.my_recipe_book.domain.enums.RoleName;

public record CreateUserDto(
        String email,
        String password,
        RoleName role
) {
}
