package com.leonardo.log.domain.models;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.leonardo.log.domain.exception.NegocioException;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Entrega {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
//	TIRADO AS VALIDAÇÕES DAQUI POIS JÁ ESTÃO SENDO VALIDADAS NO DTO DE INPUT
//	@Valid essa anotação aqui informa que o cliente tbm deve ser validado
//	@ConvertGroup(from = Default.class, to = ValidationGroups.ClienteId.class) converte para ser usado outro validation, que não seja o default
//	@NotNull					      isso sera feito somente para esse caso, na entrada do clienteController a validação ocorre normal, default
	@ManyToOne //padrão no banco esse atributo sera mapeado para a coluna cliente_id(cliene - nome da propriedade/ id - fk, nome do identificador na classe Cliente)
	private Cliente cliente; //para alterar nome da coluna usamos @JoinColumn(name = "id_cliente")	
	
	@Embedded //destinatario não sera uma tabela a parte, ela sera "embutida" na propria tabela Entrega
	private Destinatario destinatario;
	
	private BigDecimal taxa;
	
	@OneToMany(mappedBy = "entrega", cascade = CascadeType.ALL)//indica para fazer o casteamento qunado tiver uma ocorrencia
	private List<Ocorrencia> ocorrencias = new ArrayList<>();
	
	@Enumerated(EnumType.STRING) //aqui indica que na tabela será setada a string que representa o enum indicado, se fosse ORDINAL armazanaria o numero posição do enum
	private StatusEntrega status;
	
	private OffsetDateTime dataPedido;
	
//	@JsonProperty(access = Access.READ_ONLY) não aceita cliente enviar esse atributo. nÃO NECESSÁRIO POIS TEMOS DTO DE INPUT
	private OffsetDateTime dataFinalização;

	public Ocorrencia adicionarOcorrencia(String descricao) {
			Ocorrencia ocorrencia = new Ocorrencia();
			ocorrencia.setDescricao(descricao);
			ocorrencia.setDataRegistro(OffsetDateTime.now());
			ocorrencia.setEntrega(this);
			
			this.getOcorrencias().add(ocorrencia);
			
			return ocorrencia;
	}

	public void finalizar() {
		if (naoPodeSerFinalizada()) {
			throw new NegocioException("Entrega não pode ser finalizada!!");
		}
		this.setStatus(StatusEntrega.FINALIZADA);
		this.setDataFinalização(OffsetDateTime.now());
	}
	
	private boolean podeSerFinalizada() {
		return this.getStatus().equals(StatusEntrega.PENDENTE);
	}
	
	private boolean naoPodeSerFinalizada() {
		return !this.podeSerFinalizada();
	}
	
}
