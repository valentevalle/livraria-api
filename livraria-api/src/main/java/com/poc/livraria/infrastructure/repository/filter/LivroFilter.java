package com.poc.livraria.infrastructure.repository.filter;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author Andre Valle
 * 
 * Classe responsável pela consulta dinâmica de livro
 */
public class LivroFilter {

	private String isbn;
	private String nome;
	private String autor;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataPublicacaoDe;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataPublicacaoAte;
	private Double preco;
	
	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public LocalDate getDataPublicacaoDe() {
		return dataPublicacaoDe;
	}

	public void setDataPublicacaoDe(LocalDate dataPublicacaoDe) {
		this.dataPublicacaoDe = dataPublicacaoDe;
	}

	public LocalDate getDataPublicacaoAte() {
		return dataPublicacaoAte;
	}

	public void setDataPublicacaoAte(LocalDate dataPublicacaoAte) {
		this.dataPublicacaoAte = dataPublicacaoAte;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}



	
}
