package com.leonardo.log.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leonardo.log.domain.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	List<Cliente> findByNome(String nome); //o nome do metodo segue um padrão, no caso finBy

	List<Cliente> findByNomeContaining(String nome); //Containing não é exato, faz a consulta por qualque caractere passado
	
	Optional<Cliente> findByEmail(String email);
	
}
