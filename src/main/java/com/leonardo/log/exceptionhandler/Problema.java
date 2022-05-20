package com.leonardo.log.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(Include.NON_NULL) //indica para ser incluso apenas propriedade que não forem nulas. Evita, por exemplo que quando for tratada uma exception
@Getter @Setter                //de email ja cadastrado, não ser enviado o atributo fields
public class Problema {

	private Integer status;
	private OffsetDateTime date;
	private String title;
	private List<Campo> fields;
	
	
	@AllArgsConstructor
	@Getter
	static class Campo {
		
		private String nome;
		private String mensagem;
	}
}
