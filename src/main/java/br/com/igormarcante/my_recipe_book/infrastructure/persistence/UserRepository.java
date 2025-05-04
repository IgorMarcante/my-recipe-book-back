package br.com.igormarcante.my_recipe_book.infrastructure.persistence;

import br.com.igormarcante.my_recipe_book.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}