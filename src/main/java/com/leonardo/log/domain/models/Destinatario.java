package com.leonardo.log.domain.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Embeddable
public class Destinatario {

	@NotBlank
	@Column(name = "destinatario_nome") //nomeamos a coluna aqui para ficar claro na tabela Entrega ed quem pertence o atributo
	private String nome;                //outra forma seria nomear os atributos com um prefixo destinario
	
	@NotBlank
	@Column(name = "destinatario_logradouro")
	private String logradouro;
	
	@NotBlank
	@Column(name = "destinatario_numero")
	private String numero;
	
	@NotBlank
	@Column(name = "destinatario_complemento")
	private String complemento;
	
	@NotBlank
	@Column(name = "destinatario_bairro")
	private String bairro;
}
