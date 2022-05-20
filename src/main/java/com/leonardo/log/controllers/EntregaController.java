package com.leonardo.log.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.leonardo.log.assembler.EntregaAssembler;
import com.leonardo.log.domain.models.Entrega;
import com.leonardo.log.domain.repository.EntregaRepository;
import com.leonardo.log.domain.service.FinalizacaoEntregaService;
import com.leonardo.log.domain.service.SolicitacaoEntregaService;
import com.leonardo.log.dto.EntregaDTO;
import com.leonardo.log.dto.input.EntregaInputDTO;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/entregas")
public class EntregaController {

	private SolicitacaoEntregaService solicitacaoEntregaService;
	private EntregaRepository entregaRepository;
//	private ModelMapper modelMapper;é uma library externa, logo precisamos configurar para que se torne um @bean do Spring. ver pasta common
	private EntregaAssembler entregaAssembler;
	private FinalizacaoEntregaService finalizacaoEntregaService;
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EntregaDTO solicitar(@Valid @RequestBody EntregaInputDTO entregaInput) {
		
		Entrega novaEntrega = entregaAssembler.toEntity(entregaInput);
		
		Entrega entregaSolicitada = solicitacaoEntregaService.solicitar(novaEntrega);
		return entregaAssembler.toDto(entregaSolicitada);
		
	}
	
	@GetMapping
	public List<EntregaDTO> listar() {
		return entregaAssembler.toCollectionDto(entregaRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EntregaDTO> buscar(@PathVariable Long id) {
		return entregaRepository.findById(id)
				.map(entrega -> ResponseEntity.ok(entregaAssembler.toDto(entrega)))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PutMapping("/{id}/finalizacao") //ação não crud. Esse recurso não é uma entidade
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finalizar(@PathVariable Long id) {
		finalizacaoEntregaService.finalizar(id);
	}
	
}
