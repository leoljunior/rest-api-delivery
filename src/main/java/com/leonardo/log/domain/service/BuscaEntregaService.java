package com.leonardo.log.domain.service;

import org.springframework.stereotype.Service;

import com.leonardo.log.domain.exception.EntidadeNaoEncontradaException;
import com.leonardo.log.domain.models.Entrega;
import com.leonardo.log.domain.repository.EntregaRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BuscaEntregaService {

	private EntregaRepository entregaRepository;
	
	public Entrega buscar(Long entregaId) {
		return entregaRepository.findById(entregaId)
//			CRIADO UMA NOVA EXCEPTION MAIS ESPECIFICA PARA RETORNAR 404 QUANDO ENTREGA NÃO FOR ENCONTRO(RECURSO URI NÃO EXISTIR)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Entrega não encontrada."));
	}
	
}
