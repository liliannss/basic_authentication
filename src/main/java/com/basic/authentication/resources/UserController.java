package com.basic.authentication.resources;

import com.basic.authentication.models.dtos.UserDTO;
import com.basic.authentication.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Api(description = "CRUD de Usuários")
public class UserController {

    private final UserService userService;

    private UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Cria usuário na Base de Dados")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Usuário criado com sucesso")
    })
    public UserDTO createNewUser(@RequestBody UserDTO userDTO) {
        return userService.createNewUser(userDTO);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Consulta usuários na Base de Dados")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Consulta realizada com sucesso")
    })
    public Page<UserDTO> findAllUsers(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sort", defaultValue = "email") String sort,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction
    ) {
        return userService.findAllUsers(PageRequest.of(page, size, Sort.by(Sort.Direction.valueOf(direction), sort)));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Atualiza usuário por id na Base de Dados")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Atualização realizada com sucesso"),
            @ApiResponse(code = 404, message = "Usuário não encontrado")
    })
    public void updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        userService.updateUser(id, userDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Deleta usuário por id na Base de Dados")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Exclusão realizada com sucesso"),
            @ApiResponse(code = 404, message = "Usuário não encontrado")
    })
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

}