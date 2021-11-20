package com.empowerenergy.empowerenergy.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.empowerenergy.empowerenergy.model.UserLogin;
import com.empowerenergy.empowerenergy.service.UsuarioService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.empowerenergy.empowerenergy.model.UsuarioModel;
import com.empowerenergy.empowerenergy.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/v1/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {

	@Autowired
	private UsuarioRepository repositorio;
	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/todos")
	public ResponseEntity<List<UsuarioModel>> getAll(){
		if(repositorio.findAll().isEmpty()) {
			return ResponseEntity.status(204).build();
		}
		else {
			return ResponseEntity.status(200).body(repositorio.findAll());
		}
	}
		
	@GetMapping("/{id_usuario}")	
	 public ResponseEntity<UsuarioModel> getById(@PathVariable(value = "id_usuario") Long idUsuario) {
	        return repositorio.findById(idUsuario).map(resp -> ResponseEntity.status(200).body(resp))
	                .orElse(ResponseEntity.status(400).build());
	    }
	
		
	/*@PostMapping("/salvar")
	public ResponseEntity<UsuarioModel> salvar(@Valid @RequestBody UsuarioModel novaUsuario){
		return ResponseEntity.status(201).body(repositorio.save(novaUsuario));
		
	}*/
	
	/*@PutMapping("/atualizar")
	public ResponseEntity<UsuarioModel> atualizar(@Valid @RequestBody UsuarioModel novoUsuario) {
		return ResponseEntity.status(201).body(repositorio.save(novoUsuario));
	}*/

	@ApiOperation(value = "Atualizar usuario existente")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Retorna usuario atualizado"),
			@ApiResponse(code = 400, message = "Id de usuario invalido")
	})
	@PutMapping("/atualizar")
	public ResponseEntity<UsuarioModel> atualizar(@Valid @RequestBody UsuarioModel novoUsuario) {
		return usuarioService.atualizarUsuario(novoUsuario).map(resp -> ResponseEntity.status(201).body(resp))
				.orElseThrow(() -> {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
							"Necessario que passe um idUsuario valido para alterar!.");
				});

	}

	@DeleteMapping("/deletar/{id_usuario}")
	public ResponseEntity<UsuarioModel> deletar(@PathVariable(value = "id_usuario") Long idUsuario) {
		Optional<UsuarioModel> objetoOptional = repositorio.findById(idUsuario);
		if (objetoOptional.isPresent()) {
			repositorio.deleteById(idUsuario);
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(400).build();
		}
	}

	@ApiOperation(value = "Autentica usuario no sistema")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Retorna credenciais de usuario"),
			@ApiResponse(code = 400, message = "Erro na requisição!")
	})
	@PostMapping("/logar")
	public ResponseEntity<UserLogin> Authentication(@Valid @RequestBody Optional<UserLogin> user) {
		return usuarioService.Logar(user).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<Object> salvar(@Valid @RequestBody UsuarioModel usuario) {
		return usuarioService.CadastrarUsuario(usuario).map(resp -> ResponseEntity.status(201).body(resp))
				.orElseThrow(() -> {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
							"Email existente, cadastre outro email!.");
				});

	}
}