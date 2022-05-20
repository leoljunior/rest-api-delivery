package com.leonardo.log.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.leonardo.log.assembler.OcorrenciaAssembler;
import com.leonardo.log.domain.models.Entrega;
import com.leonardo.log.domain.models.Ocorrencia;
import com.leonardo.log.domain.service.BuscaEntregaService;
import com.leonardo.log.domain.service.RegistroOcorrenciaService;
import com.leonardo.log.dto.OcorrenciaDTO;
import com.leonardo.log.dto.input.OcorrenciaInputDTO;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/entregas/{id}/ocorrencias")
public class OcorrenciaController {

	private BuscaEntregaService buscaEntregaService;
	private RegistroOcorrenciaService ocorrenciaService;
	private OcorrenciaAssembler ocorrenciaAssembler;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OcorrenciaDTO registrar(@PathVariable Long id, @RequestBody @Valid OcorrenciaInputDTO ocorrenciaInputDTO) {
		Ocorrencia ocorrenciaRegistrada = ocorrenciaService.registrar(id, ocorrenciaInputDTO.getDescricao());
		return ocorrenciaAssembler.toModel(ocorrenciaRegistrada);
	}
	
	@GetMapping
	public List<OcorrenciaDTO> listar(@PathVariable Long id) {
		Entrega entrega = buscaEntregaService.buscar(id);
		return ocorrenciaAssembler.toCollectionModel(entrega.getOcorrencias());
	}
}
