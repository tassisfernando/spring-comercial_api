package com.tassis.comercial.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tassis.comercial.model.Oportunidade;

public interface OportunidadeRepository extends JpaRepository<Oportunidade, Long>{
	
	//gera uma consulta com os parâmetros informados sem precisar implementar o método
	Optional<Oportunidade> findByDescricaoAndNomeProspecto(String descricao, String nomeProspecto);
	
}
