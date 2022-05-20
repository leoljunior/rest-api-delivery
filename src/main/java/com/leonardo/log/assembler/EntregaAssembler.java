package com.leonardo.log.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.leonardo.log.domain.models.Entrega;
import com.leonardo.log.dto.EntregaDTO;
import com.leonardo.log.dto.input.EntregaInputDTO;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component //se torna um componente do spring
public class EntregaAssembler {

	private ModelMapper modelMapper;
	
	public EntregaDTO toDto(Entrega entrega) {
		return modelMapper.map(entrega, EntregaDTO.class);
	}
	
	public List<EntregaDTO> toCollectionDto(List<Entrega> entregas) {
		return entregas.stream()
				.map(this::toDto)//transformando cada elemento da stream em entregaDto
				.collect(Collectors.toList()); //coverte a stream em uma lista
	}
	
	public Entrega toEntity(EntregaInputDTO entregaInput) {
		return modelMapper.map(entregaInput, Entrega.class);
	}
	
}
