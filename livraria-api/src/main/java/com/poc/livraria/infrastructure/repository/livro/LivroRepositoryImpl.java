package com.poc.livraria.infrastructure.repository.livro;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.util.StringUtils;

import com.poc.livraria.domain.Livro;
import com.poc.livraria.infrastructure.repository.filter.LivroFilter;

public class LivroRepositoryImpl implements LivroRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	/* (non-Javadoc)
	 * @see com.poc.livraria.infrastructure.repository.livro.LivroRepositoryQuery#filtrar(com.poc.livraria.infrastructure.repository.filter.LivroFilter, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Livro> filtrar(LivroFilter livroFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Livro> criteria = builder.createQuery(Livro.class);

		Root<Livro> root = criteria.from(Livro.class);
		Predicate[] predicates = criarRestricoes(livroFilter, builder, root);
		criteria.where(predicates);
		criteria.orderBy(QueryUtils.toOrders(pageable.getSort(), root, builder));

		TypedQuery<Livro> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, total(livroFilter));
	}

	
	/**
	 * Monta a lista de restrições da consulta, com base nas informações do filtro. 
	 * @param livroFilter filtro de livros
	 * @param builder construtor de restrições
	 * @param root root
	 * @return Lista de restrições gerada
	 */
	private Predicate[] criarRestricoes(LivroFilter livroFilter, CriteriaBuilder builder,
			Root<Livro> root) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (!StringUtils.isEmpty(livroFilter.getAutor())) {
			predicates.add(builder.like(builder.lower(root.get("autor")),
					'%' + livroFilter.getAutor().toLowerCase() + "%"));
		}
		if (!StringUtils.isEmpty(livroFilter.getIsbn())) {
			predicates.add(builder.like(builder.lower(root.get("isbn")),
					'%' + livroFilter.getIsbn().toLowerCase() + "%"));
		}
		if (!StringUtils.isEmpty(livroFilter.getNome())) {
			predicates.add(builder.like(builder.lower(root.get("nome")),
					'%' + livroFilter.getNome().toLowerCase() + "%"));
		}
		if (!StringUtils.isEmpty(livroFilter.getDataPublicacaoDe())) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("dataPublicacao"),
					livroFilter.getDataPublicacaoDe()));
		}
		if (!StringUtils.isEmpty(livroFilter.getDataPublicacaoAte())) {
			predicates.add(builder.lessThanOrEqualTo(root.get("dataPublicacao"),
					livroFilter.getDataPublicacaoAte()));
		}
		
		if (!StringUtils.isEmpty(livroFilter.getPreco())) {
			predicates.add(builder.equal(root.get("preco"),livroFilter.getPreco()));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

	/**
	 * contabiliza os livros registrado na base
	 * @param livroFilter filtro de livros
	 * @return total de livros cadastrados
	 */
	private Long total(LivroFilter livroFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Livro> root = criteria.from(Livro.class);

		Predicate[] predicates = criarRestricoes(livroFilter, builder, root);
		criteria.where(predicates);

		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistroPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistroPorPagina;
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistroPorPagina);
	}

}
