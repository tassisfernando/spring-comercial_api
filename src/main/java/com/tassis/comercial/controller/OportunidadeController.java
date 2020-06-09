package com.tassis.comercial.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.tassis.comercial.model.Oportunidade;
import com.tassis.comercial.repository.OportunidadeRepository;

//permite acessos de outros servers ou portas à API
@CrossOrigin

//Indicando que a classe é um controlador Rest
@RestController

//Indicando que a classe é responsável por tratar requisições Http na URI informada
@RequestMapping("/oportunidades")

public class OportunidadeController {
	
	@Autowired //informa que necessita uma instância de OportunidadeRepository aqui
	private OportunidadeRepository oportunidades;
	
	@GetMapping
	public List<Oportunidade> listar() {
		return oportunidades.findAll();
	}
	
	@GetMapping("/{id}") //coloca o placeholder indicando o que espera receber
	public ResponseEntity<Oportunidade> buscar(@PathVariable Long id) {
		Optional<Oportunidade> oportunidade = oportunidades.findById(id);
		
		if(!oportunidade.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(oportunidade.get());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED) //para retornar o status 201, próprio para a criação
	public Oportunidade adicionar(@Valid @RequestBody Oportunidade oportunidade) {
		Optional<Oportunidade> oportunidadeExistente = oportunidades.
				findByDescricaoAndNomeProspecto(oportunidade.getDescricao(), oportunidade.getNomeProspecto());
		
		if(oportunidadeExistente.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
					"Já existe uma oportunidade para este prospecto com a mesma descrição");
		}
		return oportunidades.save(oportunidade);
	}
	
	//Excluir
	
	//Atualizar
}
