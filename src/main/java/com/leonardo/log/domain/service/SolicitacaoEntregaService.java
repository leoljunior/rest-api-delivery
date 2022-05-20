package com.leonardo.log.domain.service;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;

import com.leonardo.log.domain.models.Entrega;
import com.leonardo.log.domain.models.StatusEntrega;
import com.leonardo.log.domain.repository.EntregaRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SolicitacaoEntregaService {

	private EntregaRepository entregaRepository;
	private CatalogoClienteService catalogoClienteService;

	public Entrega solicitar(Entrega entrega) {		

		// metodo buscar ve se o cliente existe e retorna ou lança exception
		entrega.setCliente(catalogoClienteService.buscar(entrega.getCliente().getId()));
		
		entrega.setStatus(StatusEntrega.PENDENTE);
		entrega.setDataPedido(OffsetDateTime.now());

		return entregaRepository.save(entrega);
	}
}
