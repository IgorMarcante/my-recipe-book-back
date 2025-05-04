package br.com.igormarcante.my_recipe_book.presentation.controller;

import br.com.igormarcante.my_recipe_book.infrastructure.security.UserService;
import br.com.igormarcante.my_recipe_book.infrastructure.security.dto.CreateUserDto;
import br.com.igormarcante.my_recipe_book.infrastructure.security.dto.LoginUserDto;
import br.com.igormarcante.my_recipe_book.infrastructure.security.dto.RecoveryJwtTokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<RecoveryJwtTokenDto> authenticateUser(@RequestBody LoginUserDto loginUserDto) {
        RecoveryJwtTokenDto token = userService.authenticateUser(loginUserDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDto createUserDto) {
        userService.createUser(createUserDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}