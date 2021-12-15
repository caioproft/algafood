package com.poc.algafood.repository;

import com.poc.algafood.domain.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {

    Optional<Cidade> findByNome(String nomeCidade);

    @Query("SELECT c from Cidade c WHERE c.nome = :nome AND c.estado.id = :id")
    Optional<Cidade> findByNomeAndEstadoId(@Param("nome") String nome, @Param("id") Long id);
}
