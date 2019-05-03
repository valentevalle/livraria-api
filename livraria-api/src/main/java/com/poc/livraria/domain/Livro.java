package com.poc.livraria.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Andre Valle
 *
 */
@Entity
@Table(name = "tbl_livro")
public class Livro {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_livro_codigo")
	@SequenceGenerator(name = "seq_livro_codigo", sequenceName = "seq_livro_codigo", allocationSize = 1)
	private Long codigo;
	@Column(unique = true)
	@Length(min = 10, max = 13)
	private String isbn;
	@NotBlank
	private String autor;
	@NotNull
	private String nome;
	@NotNull
	private BigDecimal preco;
	@Column(name = "data_publicacao")
	private LocalDate dataPublicacao;
	@Column(name = "imagem_capa")
	private String imagemCapa;
	
	
	
	public Livro(Long codigo, String isbn, String autor, String nome, BigDecimal preco, LocalDate dataPublicacao,
			String imagemCapa) {
		super();
		this.codigo = codigo;
		this.isbn = isbn;
		this.autor = autor;
		this.nome = nome;
		this.preco = preco;
		this.dataPublicacao = dataPublicacao;
		this.imagemCapa = imagemCapa;
	}

	public Livro() {
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public LocalDate getDataPublicacao() {
		return dataPublicacao;
	}

	public void setDataPublicacao(LocalDate dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}

	
	
	public String getImagemCapa() {
		return imagemCapa;
	}

	public void setImagemCapa(String imagemCapa) {
		this.imagemCapa = imagemCapa;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Livro other = (Livro) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}