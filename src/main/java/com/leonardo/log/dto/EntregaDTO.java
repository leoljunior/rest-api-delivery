package com.leonardo.log.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import com.leonardo.log.domain.models.StatusEntrega;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EntregaDTO {

	private Long id; 
	private ClienteResumoDTO cliente;	
	private DestinatarioDTO destinatario;	
	private BigDecimal taxa;	
	private StatusEntrega status;	
	private OffsetDateTime dataPedido;	
	private OffsetDateTime dataFinalização;
	private List<OcorrenciaDTO> ocorrencias;
	}
