package com.poc.livraria.infrastructure.repository.livro;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.poc.livraria.domain.Livro;
import com.poc.livraria.infrastructure.repository.filter.LivroFilter;

/**
 * @author Andre Valle
 *
 */
public interface LivroRepositoryQuery {

	/**
	 * Pesquisa livros de acordo com o filtro dinâmico 
	 * @param livroFilter filtro dinâmico 
	 * @param pageable responsável pela ordenação e paginação 
	 * @return Lista de livros ordenados e paginados
	 */
	public Page<Livro> filtrar(LivroFilter livroFilter, Pageable pageable);
}
