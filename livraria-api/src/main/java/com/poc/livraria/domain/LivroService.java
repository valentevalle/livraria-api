package com.poc.livraria.domain;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poc.livraria.infrastructure.repository.LivroRepository;

/**
 * @author Andre Valle
 *
 */
@Service
public class LivroService {
	@Autowired
	private LivroRepository livroRepository;

	/**
	 * Atualiza os dados do livro
	 * Pesquisa o livro que deseja e  atualizar os dados 
	 * 
	 * @param codigo Livro que se deseja alterar
	 * @param livro Novos dados do livro 
	 * @return Livro atualizado
	 */
	public Livro atualizar(Long codigo, Livro livro) {
		Livro livroSalvo = livroRepository.findOne(codigo);
		BeanUtils.copyProperties(livro, livroSalvo, "codigo");
		return livroRepository.save(livroSalvo);
	}

}
