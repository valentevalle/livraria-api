package com.poc.livraria.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poc.livraria.domain.Livro;
import com.poc.livraria.infrastructure.repository.livro.LivroRepositoryQuery;

/**
 * @author Andre Valle
 * Interface responável pelo acesso e manipulação do banco de dados
 */
public interface LivroRepository extends JpaRepository<Livro, Long>, LivroRepositoryQuery {
}
