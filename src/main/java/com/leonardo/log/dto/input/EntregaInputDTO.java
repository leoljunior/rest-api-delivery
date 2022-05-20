package com.leonardo.log.dto.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EntregaInputDTO {

	@Valid
	@NotNull
	private ClienteIdInputDTO cliente;
	
	@Valid
	@NotNull
	private DestinatarioInputDTO destinatario;
	
	@NotNull
	private BigDecimal taxa;
}
